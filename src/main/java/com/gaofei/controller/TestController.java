package com.gaofei.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.gaofei.domain.JwtTokenUtil;
import com.gaofei.domain.User;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author : gaofee
 * @date : 10:06 2021/4/12
 * @码云地址 : https://gitee.com/itgaofee
 */
@Controller
public class TestController {



    @Value("${win.pdf.path}")
    String winPath;
    @Value("${linux.pdf.path}")
    String linuxPath;

    @RequestMapping("/httpdemo")
    @ResponseBody
    public String test(String today, Model m)  {


//        String json = HttpUtil.post("http://127.0.0.1:9001/menu/findMenuByUid?uid="+3, "");

            HashMap<String, Object> map = new HashMap<>();
            map.put("username", "admin");
            map.put("password", "123456");
            String json = HttpUtil.post("http://localhost:9001/user/login", JSON.toJSONString(map));

            return json;




        /*User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        u1.setUsername("gaofei");
        u1.setId(1);
        u2.setUsername("gaofei2");
        u2.setId(2);
        u3.setUsername("gaofei3");
        u3.setId(3);
        List<User> list = Arrays.asList(u1,u2,u3);
        Document document = new Document();
        document.open();
        PdfWriter pw = PDFUtils.createDoc(document, winPath);
        PDFUtils.addTable(pw, new String[]{"姓名", "id"}, new String[]{"username", "id"}, list);
        document.close();*/

    }

    @RequestMapping("/login")
    @ResponseBody
    public Object login(User portalUser, HttpServletResponse res, Model m){
        User dbUser = new User();
        dbUser.setId(1);
        dbUser.setPassword("123");
        dbUser.setUsername("lisi");

        if (portalUser.getUsername().equals(dbUser.getUsername()) && dbUser.getPassword().equals(portalUser.getPassword())) {
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
