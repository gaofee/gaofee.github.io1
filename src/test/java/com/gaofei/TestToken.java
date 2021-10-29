package com.gaofei;

import com.gaofei.domain.JwtTokenUtil;

/**
 * @author : gaofee
 * @date : 9:31 2021/10/29
 * @码云地址 : https://feege.gitee.io
 */
public class TestToken {

    public static void main(String[] args) {
        JwtTokenUtil objectJwtTokenUtil = new JwtTokenUtil();
        boolean b = objectJwtTokenUtil.validateToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaXNpIiwiY3JlYXRlZCI6MTYzNTQ3MTAyOTYwNywiZXhwIjoxNjM2MDc1ODI5fQ.QpbSr8VlNBtI-XPzqhm7K37E3EsiK4V255ALDdj6E6dISbFrQXdfB7mAaY1HCeFCb3Gebe0tBu7ESfkR8f9fBA");
        System.out.println("是否合法:"+b);

        String name = objectJwtTokenUtil.getUserNameFromToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsaXNpIiwiY3JlYXRlZCI6MTYzNTQ3MTAyOTYwNywiZXhwIjoxNjM2MDc1ODI5fQ.QpbSr8VlNBtI-XPzqhm7K37E3EsiK4V255ALDdj6E6dISbFrQXdfB7mAaY1HCeFCb3Gebe0tBu7ESfkR8f9fBA");
        System.out.println(name);
    }
}
