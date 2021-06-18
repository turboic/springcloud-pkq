package com.turboic.cloud.feign;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * open-feign默认熔断的处理类
 * @author liebe
 */
@Slf4j
@Component
public class DemoFeignServiceFallBack implements DemoFeignService {
    @Override
    public String feignInvoke(String feign) {
        log.info("熔断，默认回调函数");
        return "{\"id\":\"open-feign微服务调用\",\"name\":\"open-feign\",\"password\":\"服务降级\",\"description\":\"使用open-feign调用微服务出现异常，您看到的是降级效果\"}";
    }
}