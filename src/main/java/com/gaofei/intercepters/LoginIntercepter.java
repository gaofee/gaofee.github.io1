package com.gaofei.intercepters;

import cn.hutool.json.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gaofei.domain.JwtTokenUtil;
import com.gaofei.domain.User;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : gaofee
 * @date : 15:37 2021/4/12
 * @码云地址 : https://gitee.com/itgaofee
 */
public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if(uri.indexOf("/login")>=0 ){
            return true;
        }

        //获取请求头中的token
        String headerToken = request.getHeader("Authorization");
        if(headerToken==null||headerToken.equals("")){
            //不放行,并且返回401
            try {
                // 封装错误信息
                Map<String, Object> responseData = new HashMap();
                responseData.put("code", 401);
                responseData.put("message", "err request");
                responseData.put("cause", "Token is empty");
                // 将信息转换为 JSON,然后写到前台
                PrintWriter out = response.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                //Servlet不能直接返回json数据，需要转成JSONString

                String info = objectMapper.writeValueAsString(responseData);

                out.write(info);


                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        //然后验证token的合法性,比如是否过期,是否有效等
        if(headerToken!=null){
            JwtTokenUtil<User> jwtTokenUtil = new JwtTokenUtil<>();
            if(jwtTokenUtil.validateToken(headerToken)){
                // 封装正确信息
                Map<String, Object> responseData = new HashMap();
                responseData.put("code", 200);
                responseData.put("message", "success");
                responseData.put("cause", "Token is right");
                // 将信息转换为 JSON,然后写到前台
                PrintWriter out = response.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                //Servlet不能直接返回json数据，需要转成JSONString

                String info = objectMapper.writeValueAsString(responseData);

                out.write(info);
            };
            return true;
        }
        return false;
    }
}
