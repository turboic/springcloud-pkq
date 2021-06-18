package com.turboic.cloud.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liebe
 *
 * 访问地址
 * http://localhost:9601/dingding
 */
@RestController
@RefreshScope
public class DingdingController {
    private static final Logger logger = LoggerFactory.getLogger(DingdingController.class);

    @Value("${dingding.secret}")
    private String secret;
    @Value("${dingding.urlAccessToken}")
    private String urlAccessToken;



    @RequestMapping("/dingding")
    public String dingding(){
        logger.error("请求dingding方法了");
        return secret+"||"+urlAccessToken;
    }
}
