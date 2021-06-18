package com.turboic.cloud.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 */
@Component
public class IFeignDeliveryServiceFallbackFactory implements FallbackFactory<IFeignDeliveryServiceClient> {
    @Override
    public IFeignDeliveryServiceClient create(Throwable cause) {
        return orderNumber -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "美团骑手正在配送中!!!";
        };
    }
}