package com.turboic.cloud.service.impl;

import com.turboic.cloud.service.CircuitBreakerService;
import com.turboic.cloud.vo.App;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author liebe
 */
@Service
@Slf4j
public class CircuitBreakerImpl implements CircuitBreakerService {
    @Override
    public App circuitGet(String id) {
        log.info("CircuitBreakerImpl的方法circuitGet被调用:{}",id);
        App app = new App();
        app.setVersion("2.0");
        app.setName("正常接口调用返回");
        app.setDescription("SpringCloud之熔断器Hystrix");
        app.setDate(new Date());
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            log.error("线程sleep异常:{}",e);
        }
        return app;
    }
}
