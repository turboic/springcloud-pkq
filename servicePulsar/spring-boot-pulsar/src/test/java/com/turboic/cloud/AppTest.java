package com.turboic.cloud;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.turboic.cloud.config.Msg;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */

public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        String header = "";
        String body = "";
        Snowflake snowflake = IdUtil.getSnowflake(6, 6);
        Msg msg = new Msg(snowflake.nextId(), header, body);

        JSONObject jsonObject = JSONUtil.parseObj(msg);

        System.err.println(jsonObject.toString());

        java.util.Map<String, Object> headers = new HashMap<>();
        headers.put("Authorization", "liebe");
        headers.put("id", 1);
        Map<String, Object> payload = new HashMap<>();
        payload.put("data", "人生如逆旅，我亦是行人");
        payload.put("timestamp", new Timestamp(System.currentTimeMillis()));
        payload.put("msg", "请求成功");
        byte[] key = new byte[0];
        try {
            key = "心有所信，方能远行。".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String token = JWTUtil.createToken(headers, payload, key);
        System.err.println(token);
    }
}
