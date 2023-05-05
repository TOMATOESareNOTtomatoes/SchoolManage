package com.fanqie.manage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fanqie.commonutils.utils.RandomNumberGenerator;
import com.fanqie.commonutils.utils.UUIDStringUtils;
import com.fanqie.manage.entity.*;
import com.fanqie.manage.entity.Class;
import com.fanqie.manage.mapper.*;
import com.fanqie.manage.param.excelDemo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;

/**
 * 完成数据导入的监听器，easyExcel模板改的
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class DemoDataListener implements ReadListener<excelDemo> {

    private MainMapper mainMapper;

    private TeachMapper teachMapper;

    private UserMapper userMapper;

    private ClassMapper classMapper;

    private TeachClassMapper teachClassMapper;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param
     */
    public DemoDataListener(MainMapper mainMapper) {
        this.mainMapper = mainMapper;
    }


    /**
     * todo：没有用到，现在是解析一条信息，存储一次数据库。
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
     * 缓存的数据
     */
    private List<excelDemo> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public DemoDataListener(UserMapper userMapper, TeachMapper teachMapper,
                            TeachClassMapper teachClassMapper, ClassMapper classMapper,
                            MainMapper mainMapper) {
        this.mainMapper = mainMapper;
        this.classMapper=classMapper;
        this.userMapper=userMapper;
        this.teachClassMapper=teachClassMapper;
        this.teachMapper=teachMapper;

    }


    /**
     * 这个每一条数据解析都会来调用
     * <p>
     * 处理流程：
     * 1、直接根据读到的一条数据生成一条《main》信息
     * TODO：发现表里没有教师的id，无法保证教师的名称的唯一性，可能会有问题
     * 2、用随机数生成工具生成唯一的id值保存进《main》里面，
     * 3、根据上面生成的唯一id，创建《teach》表的数据，记录课程相关信息。
     * 4、查询《class》表，查看有没有这个班、有就返回id，没有就创建一个班。
     * 5、根据查询到的班，和上面的唯一id，创建一条《teachClass》信息，用于记录教师教授的班级
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(excelDemo data, AnalysisContext context) {
        //根据名字和院系查询 id ，
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name", data.getUserName());
        map.put("faculty", data.getFaculty());
        //TODO：理论上只会存在一条记录，如果存在多个，取第一个
        List<User> users = userMapper.selectByMap(map);
        if (users.isEmpty()) {
            //查询不到记录就添加
            User user = new User();
            user.setUserName(data.getUserName());
            user.setFaculty(data.getFaculty());
            user.setPassword("12345");
            user.setNumber("12345678909");
            int randomNum = RandomNumberGenerator.generate();
            String s= String.valueOf(randomNum);
            user.setUserId(s);
            int i = userMapper.insert(user);
            if (i == 1) {
                System.out.printf("成功添加一条教师数据");
            } else System.out.printf("监听器 添加教师数据失败");

            users = userMapper.selectByMap(map);
            if (users.isEmpty()) {
                System.out.printf("查询不到教师数据");
            }
        }
        //查到教师的id了 在《main》表添加一条记录
        String s = UUIDStringUtils.randomUUID();//三个表都存储的唯一字符串
        Main main=new Main();

        main.setUserId(String.valueOf(users.get(0).getUserId()));//TODO:这里默认选择第一条记录
        main.setTerm(data.getTerm());
        main.setUniqueNumber(s);

        int number=mainMapper.insert(main);
        if(number!=1){
            System.out.println("添加《main》数据失败");
        }
        //添加课程信息
        Teach teach=new Teach();
        teach.setTeachId(s);
        teach.setTeachName(data.getTeachName());
        //todo:优化：通过过滤器实现赋值。
        if(data.getPracticalHours()==null){
            data.setPracticalHours("0");
        }
        if(data.getTheoreticalHours()==null){
            data.setTheoreticalHours("0");
        }
        teach.setPracticalHours(data.getPracticalHours());
        teach.setTheoreticalHours(data.getTheoreticalHours());
        number=teachMapper.insert(teach);
        if(number!=1){
            System.out.println("添加《teach》数据失败");
        }
        //添加班级信息，需要先查询班级id。没有就添加
        String[] arr = data.getClassName().split(",");
        System.out.print("班级："+data.getClassName()+"\n");
        for (String y : arr) {
            //System.out.print("一个班级："+y+"\n");
            QueryWrapper queryWrapper=new QueryWrapper();
            queryWrapper.eq("class_name",y);
            Class aClass = classMapper.selectOne(queryWrapper);
            if(aClass == null){
                //查询不到班级信息，添加班级
                Class c=new Class();
                c.setClassName(y);
                c.setClassId(RandomNumberGenerator.generate());
                int cn= Integer.parseInt(data.getClassNumber());
                c.setClassNumber((cn/arr.length));
                int i=classMapper.insert(c);
                if(i!=1){
                    System.out.printf("添加班级信息失败");
                }else {
                    aClass = classMapper.selectOne(queryWrapper);
                    if (aClass == null) {
                        System.out.printf("查询班级id出错");
                    }
                }
            }
            //查询到班级id  添加teachClass表信息
            TeachClass teachClass=new TeachClass();
            teachClass.setClassId(aClass.getClassId());
            teachClass.setUniqueNumber(s);
            int itc=teachClassMapper.insert(teachClass);
            if(itc!=1){
                System.out.printf("插入班级-教授信息失败");
            }

        }
    }


    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
//            MainMapper;
        log.info("存储数据库成功！");
    }
}
