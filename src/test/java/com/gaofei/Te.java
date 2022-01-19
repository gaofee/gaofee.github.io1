package com.gaofei;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

/**
 * @author : gaofee
 * @date : 10:57 2021/11/5
 * @码云地址 : https://feege.gitee.io
 */
public class Te {
    public static void main(String[] args) {

        String s = "{\n    \"queryConditions\":\"[{\\\"queryField\\\":\\\"project_no\\\",\\\"queryType\\\":\\\"BEEQUAL TO\\\",\\\"queryValue\\\":\\\"1\\\"}]\",\n    \"orderConditions\":\"[{\\\"orderField\\\":\\\"id\\\",\\\"orderType\\\":\\\"asc\\\"}]\",\n    \"selectFields\":\"*\",\n    \"pageNo\":1,\n    \"pageSize\":3,\n    \"tableName\":\"project\"\n}";
        JSONObject parse = JSON.parseObject(s);
        System.out.println(parse);

//        HashMap<String, String> map = new HashMap<>();
//        map.put("name", "zhangsan");
//        String s1 = JSON.toJSONString(map);
//        System.out.println(s1);
    }
}
