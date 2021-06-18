package com.turboic.cloud.feign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
/**
 * @author liebe
 */
@Component
public class IFeignPlatformServiceFallbackFactory implements FallbackFactory<IFeignPlatformService> {
    @Override
    public IFeignPlatformService create(Throwable cause) {
        final IFeignPlatformService iFeignPlatformService = woUser1 -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        };
        return iFeignPlatformService;
    }
}