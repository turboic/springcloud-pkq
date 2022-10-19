package com.turboic.cloud.fallback;
import com.turboic.cloud.openfeign.OpenFeignService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OpenFeignServiceFallbackServiceFactory implements FallbackFactory<OpenFeignService> {
    @Override
    public OpenFeignFallbackService create(Throwable throwable) {
        return new OpenFeignFallbackService(throwable);
    }
}