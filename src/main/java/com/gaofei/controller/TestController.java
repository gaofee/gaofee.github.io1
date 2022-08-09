package com.gaofei.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaofei.domain.JwtTokenUtil;
import com.gaofei.domain.User;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import jdk.nashorn.internal.parser.Token;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : gaofee
 * @date : 10:06 2021/4/12
 * @码云地址 : https://gitee.com/itgaofee
 */
@Controller
public class TestController {


    /*
     * 微信测试账号推送
     * */
    @PostMapping("/push")
    @ResponseBody
    public void push() throws InterruptedException {
        //1，配置
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        wxStorage.setAppId("wx5bc2a5a44a16898f");
        wxStorage.setSecret("b4ed2d0916d8bd0e60d0");
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxStorage);

        //获取所有关注测试号的用户openId
        List<String> wechatAllUser = getWechatAllUser();

        //实现了多人推送
        wechatAllUser.forEach(openId->{
            //2,推送消息
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(openId)//要推送的用户openid

                    .templateId("QwK5m9ZObZEpT1MUn18VIwX9mE4CpgYM6kkIIJC7GKA")//模版id
                    .url("https://30paotui.com/")//点击模版消息要访问的网址
                    .build();
            //3,如果是正式版发送模版消息，这里需要配置你的信息
            //        templateMessage.addData(new WxMpTemplateData("name", "value", "#FF00FF"));
            //                templateMessage.addData(new WxMpTemplateData(name2, value2, color2));
            try {
                wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
            } catch (Exception e) {
                System.out.println("推送失败：" + e.getMessage());
                e.printStackTrace();
            }
        });



    }

    /**
     * 获取所有的用户
     * @param
     * @return
     */
    public List<String> getWechatAllUser() throws InterruptedException {
        String tokenObject = HttpUtil.get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx5bc2a5a44a16898f&secret=b4ed2d0916d8bd0e60d065a096061509");
        JSONObject jsonObject = JSON.parseObject(tokenObject);
        String access_token =(String) jsonObject.get("access_token");

        System.out.println("=========获取所有用户的openid====token=========="+access_token);
        String AllUser =  HttpUtil.get("https://api.weixin.qq.com/cgi-bin/user/get?access_token="+access_token ) ;
        Map<String, Object> getUser_result = JSON.parseObject(AllUser);
        Map<String, Object> result = (Map<String, Object>) getUser_result.get("data");
        List<String> userList = null;
        if(!"".equals(result.get("count"))){
            userList =  (List<String>) result.get("openid");
        }

        return userList;
    }




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
