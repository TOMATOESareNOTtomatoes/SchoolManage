package com.fanqie.manage.listener;

import com.fanqie.commonutils.utils.JwtUtils;
import com.fanqie.commonutils.utils.Permission;
import com.fanqie.commonutils.utils.PermissionEnum;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

public class TokenInterceptor implements HandlerInterceptor {

    // 在请求处理之前调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("token"); // 获取请求头中的 token
        //System.out.println("token"+token);
        if (token == null) {
            System.out.println("token为空");
            return false;
        }
//TODO：*********跳转问题
        if (JwtUtils.isTokenExpired(token)) {//判断是否过期
            System.out.println("token过期了");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.sendRedirect("http://localhost:5173/"); // token 无效，重定向到登录页面
            //return R.lapsed(); // 请求不再继续处理
            return false;
        }

        if (!isValidToken(token)) {//判断token是否有效
            response.setHeader("Access-Control-Allow-Origin", "");
            response.sendRedirect("http://localhost:5173"); // token 无效，重定向到登录页面
            return false; // 请求不再继续处理
        }

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            System.out.println("该方法没有设置权限");
            return true;
        }
        System.out.println("该方法有权限设置");
        // 获取方法中的注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 获取类注解
        Permission permissionClass = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), Permission.class);
        // 获取方法注解
        Permission permissionMethod = AnnotationUtils.findAnnotation(method, Permission.class);

        // 判断是否需要权限校验
        if (permissionClass == null && permissionMethod == null) {
            // 不需要校验权限，直接放行
            return true;
            //System.out.println("该接口没有权限声明");
        }
        // 获取该方法注解，优先级:方法注解>类注解
        PermissionEnum[] permissionEnums;
        if (permissionClass != null && permissionMethod == null) {
            // 类注解不为空，方法注解为空，使用类注解
            permissionEnums = permissionClass.name();
        } else if (permissionClass == null) {
            // 类注解为空，使用方法注解
            permissionEnums = permissionMethod.name();
        } else {
            // 都不为空，使用方法注解
            permissionEnums = permissionMethod.name();
        }
        //System.out.println("要校验的权限名称："+ Arrays.toString(permissionEnums));
        //获取用户的 id
        //String userId = JwtUtils.getMemberIdByJwtToken(request);
        //获取用户权限
        String permission = JwtUtils.getMemberPermissionByJwtToken(request);

        for (PermissionEnum value : permissionEnums) {
            if (Objects.equals(permission, value.getCode())) {
                // 你的代码
                System.out.println("权限通过！" + permissionEnums);
                return true;
            }
        }
        System.out.println("权限不通过："+permissionEnums.toString());
        return false;
        //TODO:return false
        //return true;
    }

    // 在请求处理之后调用，但在视图被渲染之前（Controller 方法调用之后）
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 不需要实现任何逻辑
    }

    // 在整个请求结束之后调用，也就是在 DispatcherServlet 渲染了视图之后执行（主要用于清理资源）
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 不需要实现任何逻辑
    }

    // 检查 token 是否有效
    private boolean isValidToken(String token) {
        System.out.println("检查token了");
        return JwtUtils.checkToken(token);
    }
}