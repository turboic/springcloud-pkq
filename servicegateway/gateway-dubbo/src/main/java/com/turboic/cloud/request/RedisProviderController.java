package com.turboic.cloud.request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author liebe
 * 测试网关限流的接口类，声明longAdder属性，用于记录实际的请求此时啊
 * 每秒1000次请求，电脑的性能已经飘了
 */
@RestController
@RequestMapping("/gateway")
@Slf4j
public class RedisProviderController {
    private LongAdder longAdder = new LongAdder();

    @RequestMapping("/redis")
    public String redis(){
        longAdder.increment();
        log.info("通过gateway路由测试转发到此处的Redis限流服务接口了");
        log.info("hello redis server");
        log.info("用于记录redis接口请求限流的次数:{}",longAdder.intValue());
        return "hi,redis,从入门到精通吧";
    }
}
