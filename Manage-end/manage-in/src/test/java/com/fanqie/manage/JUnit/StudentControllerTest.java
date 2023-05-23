//package com.fanqie.manage.JUnit;
//
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MainApplication.class)
//public class StudentControllerTest {
//
//	// 注入Spring容器
//    @Autowired
//    private WebApplicationContext applicationContext;
//    // 模拟Http请求
//    private MockMvc mockMvc;
//
//    @Before
//    public void setupMockMvc(){
//    	// 初始化MockMvc对象
//        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
//    }
//
//    /**
//     * 新增学生测试用例
//     * @throws Exception
//     */
//    @Test
//    public void addStudent() throws Exception{
//        String json="{"name":"张三","className":"三年级一班","age":"20","sex":"男"}";
//        mockMvc.perform(MockMvcRequestBuilders.post("/student/save")    //构造一个post请求
//                    // 发送端和接收端数据格式
//                    .contentType(MediaType.APPLICATION_JSON_UTF8)
//                    .accept(MediaType.APPLICATION_JSON_UTF8)
//                    .content(json.getBytes())
//            )
//           // 断言校验返回的code编码
//           .andExpect(MockMvcResultMatchers.status().isOk())
//           // 添加处理器打印返回结果
//           .andDo(MockMvcResultHandlers.print());
//    }
//}
//
//作者：京东云开发者
//链接：https://juejin.cn/post/7203143824012804157
//来源：稀土掘金
//著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。