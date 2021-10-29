package com.gaofei.controller;

import com.gaofei.domain.JwtTokenUtil;
import com.gaofei.domain.User;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author : gaofee
 * @date : 10:06 2021/4/12
 * @码云地址 : https://gitee.com/itgaofee
 */
@Controller
public class TestController {

    @RequestMapping("/test")
    @ResponseBody
    public String test(String today,Model m){

        if(today==null|| today==""){
            today=new Date().toString();
        }

        User user = new User();
        user.setId(1);
        user.setName("jake");
        User user1 = new User();
        user1.setName("rose");
        user1.setId(2);
        List<User> objects = Arrays.asList(user,user1);
        m.addAttribute("users", objects);
        return "hello";
    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestBody User portalUser, HttpServletResponse res, Model m){
        User dbUser = new User();
        dbUser.setId(1);
        dbUser.setPassword("123");
        dbUser.setUsername("lisi");

        if (portalUser.getUsername().equals(dbUser.getUsername()) && dbUser.getPassword().equals(portalUser.getPassword())) {
            //登录成功
            JwtTokenUtil jwt = new JwtTokenUtil();
            String token = jwt.generateToken(dbUser);
            System.out.println(token+"***************");
            res.setHeader("Authorization",token);
            m.addAttribute("token",token);
            return token;
        }
        return null;
    }

    @RequestMapping("validate")
    @ResponseBody
    public Object validate(){
        JwtTokenUtil<User> tokenUtil = new JwtTokenUtil<>();

        boolean b = tokenUtil.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaXNpIiwiY3JlYXRlZCI6MTYxODIwOTEwNzM2OCwiZXhwIjoxNjE4ODEzOTA3fQ.lMmtAfMRilQBbHcGmj0YVtV4sEPCuKRk-1u98tkXTW4f1WKYDFnuyaA46H7sunTyZkbexq9SSegdIcAbPQqtvg");
        System.out.println("token有效?"+b);
        String username = tokenUtil.getUserNameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaXNpIiwiY3JlYXRlZCI6MTYxODIwOTEwNzM2OCwiZXhwIjoxNjE4ODEzOTA3fQ.lMmtAfMRilQBbHcGmj0YVtV4sEPCuKRk-1u98tkXTW4f1WKYDFnuyaA46H7sunTyZkbexq9SSegdIcAbPQqtvg");
        System.out.println("username="+username);
        String newToken = tokenUtil.refreshHeadToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaXNpIiwiY3JlYXRlZCI6MTYxODIwOTEwNzM2OCwiZXhwIjoxNjE4ODEzOTA3fQ.lMmtAfMRilQBbHcGmj0YVtV4sEPCuKRk-1u98tkXTW4f1WKYDFnuyaA46H7sunTyZkbexq9SSegdIcAbPQqtvg");
        System.out.println("新的token为:"+newToken);
        return b;
    }

}
