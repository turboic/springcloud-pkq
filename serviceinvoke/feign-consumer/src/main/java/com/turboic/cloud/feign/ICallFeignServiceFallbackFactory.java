package com.turboic.cloud.feign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 * 异常出现时替换ICallFeignServiceClient，Fiegn客户端的相应结果
 */
@Component
public class ICallFeignServiceFallbackFactory implements FallbackFactory<ICallFeignServiceClient> {
    @Override
    public ICallFeignServiceClient create(Throwable cause) {
        return () -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "服务器出现了一点点小故障，请稍后重试吧!!!";
        };
    }
}