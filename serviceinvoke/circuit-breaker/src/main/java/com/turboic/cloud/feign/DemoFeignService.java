package com.turboic.cloud.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author liebe
 */
@FeignClient(value = "provider-service", fallback = DemoFeignServiceFallBack.class)
public interface DemoFeignService {
    @RequestMapping(value = "/provider-service/feign")
    String feignInvoke(@RequestParam("feign") String feign);
}