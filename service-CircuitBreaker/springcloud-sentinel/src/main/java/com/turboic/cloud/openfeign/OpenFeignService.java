package com.turboic.cloud.openfeign;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.turboic.cloud.fallback.OpenFeignServiceFallbackServiceFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "sentinel-nacos-provider", fallbackFactory = OpenFeignServiceFallbackServiceFactory.class,configuration = OpenFeignService.FeignConfiguration.class)
public interface OpenFeignService{

    /**
     * openFeign接口
     * @param param
     * @return
     */
    @GetMapping("/openFeign/{param}")
    String cloud(@PathVariable("param") String param);

    class FeignConfiguration {
        @Bean
        public EchoServiceFallback echoServiceFallback() {
            return new EchoServiceFallback();
        }
    }

    class EchoServiceFallback implements OpenFeignService {
        @Override
        public String cloud(String param) {
            return "服务熔断降级,官方提供的方法:"+param+",异常信息:";
        }
    }
}
