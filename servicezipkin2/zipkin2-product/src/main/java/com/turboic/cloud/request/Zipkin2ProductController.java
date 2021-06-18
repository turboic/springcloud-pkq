package com.turboic.cloud.request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author taiji
 */
@RestController
@RequestMapping("/product")
public class Zipkin2ProductController {
    private static final Logger logger = LoggerFactory.getLogger(Zipkin2ProductController.class);

    @PostMapping("/hello")
    public String product(String name){
        logger.error("消费者客户端正在远程调用provider服务");
        logger.info("request parameter name:{}",name);
        return "晚上好，该睡觉了，哈哈哈哈哈哈 "+name;
    }
}
