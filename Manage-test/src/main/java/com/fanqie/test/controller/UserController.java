package com.fanqie.test.controller;


import com.alibaba.excel.EasyExcel;
import com.fanqie.commonutils.utils.JwtUtils;
import com.fanqie.commonutils.utils.R;
import com.fanqie.test.EasyExcel.DemoDAO;
import com.fanqie.test.EasyExcel.DemoData;
import com.fanqie.test.EasyExcel.DemoDataListener;
import com.fanqie.test.entity.User;
import com.fanqie.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fq
 * @since 2023-03-14
 */
@RestController
@RequestMapping("/test")
//@CrossOrigin//可以简单解决跨越问题
public class UserController {

    @Autowired
    UserService service;
//前端请求传Json对象则后端使用@RequestParam；
//
//前端请求传Json对象的字符串则后端使用@RequestBody。

    @PostMapping("test1")
    public R test1(@RequestBody User user){
        System.out.println("测试接口："+user);
        user.setUserId(11);
        String token=service.login(user);
        System.out.println("token："+token);
        return R.ok().data("token",token);
    }
/*
Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported
@RequestBody  用这个接收 axios ajas 请求就会出现上面的错误
 */

    //Required String parameter 'userName' is not present
    //@RequestParam 接收 form-data 表单的数据就会出现上面的错误

    @PostMapping("test2")
    public R test2(@RequestParam String userName,@RequestParam String password){
        System.out.println("测试接口："+userName+"----"+password);
        return R.ok();
    }

    @GetMapping("test3")
    public R test3(HttpServletRequest request){
        System.out.println(request);
        //通过token查询 id
        String id= JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("token的id"+id);
        //查询数据库。返回对象
        User user=new User();
        user.setUserId(11);
        user.setUserName("lisi");
        user.setPassword("111111");
        return R.ok().data("user",user);
    }

//    private List<DownloadData> data() {
//        List<DownloadData> list = new ArrayList<DownloadData>();
//        for (int i = 0; i < 10; i++) {
//            DownloadData data = new DownloadData();
//            data.setString("字符串" + i);
//            data.setDate(new Date());
//            data.setDoubleData(0.56);
//            list.add(data);
//        }
//        return list;
//    }
//
//    /**
//     * 文件下载（失败了会返回一个有部分数据的Excel）
//     * <p>
//     * 1. 创建excel对应的实体对象 参照{@link DownloadData}
//     * <p>
//     * 2. 设置返回的 参数
//     * <p>
//     * 3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
//     */
//    @GetMapping("download")
//    public void download(HttpServletResponse response) throws IOException {
//        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
//        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//        response.setCharacterEncoding("utf-8");
//        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
//        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
//        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
//        EasyExcel.write(response.getOutputStream(), DownloadData.class).sheet("模板").doWrite(data());
//    }
//
//
    /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
     */
    public DemoDAO demoDAO=new DemoDAO();//service对象里的数据库存储方法。service对象就好

    @PostMapping("upload")
    @ResponseBody
    public R upload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener(demoDAO)).sheet().doRead();
        return R.ok();
    }

}

