//
//import com.baomidou.mybatisplus.annotation.DbType;
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.generator.AutoGenerator;
//import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
//import com.baomidou.mybatisplus.generator.config.GlobalConfig;
//import com.baomidou.mybatisplus.generator.config.PackageConfig;
//import com.baomidou.mybatisplus.generator.config.StrategyConfig;
//import com.baomidou.mybatisplus.generator.config.rules.DateType;
//import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
//import org.junit.Test;
//
//
//public class CodeGenerator {
//
//    @Test
//    public void run() {
//
//        // 1、创建代码生成器
//        AutoGenerator mpg = new AutoGenerator();
//
//        // 2、全局配置
//        GlobalConfig gc = new GlobalConfig();
//        //String projectPath = System.getProperty("user.dir");//自动获取项目路径，可能有问题
//        gc.setOutputDir("C:\\Users\\fanqie\\Desktop\\JavaProject\\Manage-end\\manage-in" + "/src/main/java");//项目路径-----------------------
//
//        gc.setAuthor("fq");//作者
//        gc.setOpen(false); //生成后是否打开资源管理器
//        gc.setFileOverride(false); //重新生成时文件是否覆盖
//
//        //IUserServie-》UserServie
//        gc.setServiceName("%sService");	//去掉Service接口的首字母I
//
//        gc.setIdType(IdType.AUTO); //主键策略 long -》ID_WORKER       char ->ID_WORKER_STR----------------------------
//        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
//        //gc.setSwagger2(true);//开启Swagger2模式
//
//        mpg.setGlobalConfig(gc);
//
//        // 3、数据源配置
//        DataSourceConfig dsc = new DataSourceConfig();
//        dsc.setUrl("jdbc:mysql://localhost:3306/ssm23javatest?useSSL=false&useUnicode=true&characterEncoding=UTF-8");  //数据库----------------------------
//        dsc.setDriverName("com.mysql.jdbc.Driver");
//        dsc.setUsername("root");
//        dsc.setPassword("root");
//        dsc.setDbType(DbType.MYSQL);
//        mpg.setDataSource(dsc);
//
//        // 4、包配置
//        PackageConfig pc = new PackageConfig();
//
//        pc.setModuleName("manage"); //模块名------------------------
//        //包  com.atguigu.eduservice
//        pc.setParent("com.fanqie");
//        //包  com.atguigu.eduservice.controller
//        pc.setController("controller");
//        pc.setEntity("entity");
//        pc.setService("service");
//        pc.setMapper("mapper");
//        mpg.setPackageInfo(pc);
//
//        // 5、策略配置
//        StrategyConfig strategy = new StrategyConfig();
//
//        strategy.setInclude("additional_main");//数据库表达 名称--------------------------------------可以，“”多个表
//
//        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
//        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
//
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
//        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作
//
//        strategy.setRestControllerStyle(true); //restful api风格控制器
//        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符
//
//        mpg.setStrategy(strategy);
//
//        // 6、执行
//        mpg.execute();
//    }
//}
