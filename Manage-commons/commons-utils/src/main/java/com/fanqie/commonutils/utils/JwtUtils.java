package com.fanqie.commonutils.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 配置token登录的工具类
 */
public class JwtUtils {

    //token过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
    //密钥，任意值
    public static final String APP_SECRET = "abcd";
    //生成token字符串
    public static String getJwtToken(String id, String permission){

        String JwtToken = Jwts.builder()
                //设置jwt的头部信息，包含一些编码（或者说解析规则？）不变的
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                .setSubject("commons_user")//随便起的名字
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))//过期时间设置

                //设置token的主体信息，就是token里存储的信息，在这里加上去。
                .claim("id", id)
                //.claim("password", password)
                .claim("permission",permission)

                //token的加密方式
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 检查 JWT token 是否已过期
     * @param jwtToken JWT token
     * @return true 表示 token 已过期，false 表示 token 未过期
     */
    public static boolean isTokenExpired(String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken).getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken
     * @return
     */
    public static boolean checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) return false;
        try {
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
}

    /**
     * 判断token是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 根据token获取会员id
     * @param request
     * @return
     */
    public static String getMemberIdByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken))
        {
            System.out.println("token为空");
            return "";
        }
        Jws<Claims> claimsJws =
                Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        //这个方法获取token里存储的信息
        Claims claims = claimsJws.getBody();
        //通过key获取到相应的值
        return (String)claims.get("id");
    }

    /**
     * 根据token 获取用户权限
     * @param request
     * @return
     */
    public static String getMemberPermissionByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken))
        {
            System.out.println("token为空");
            return "";
        }
        Jws<Claims> claimsJws =
                Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        //这个方法获取token里存储的信息
        Claims claims = claimsJws.getBody();
        //通过key获取到相应的值
        return (String)claims.get("permission");
    }

}
