package com.turboic.cloud.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * 定义Feign服务提供者接口
 * IFeignServiceClient
 *
 * 合理配置调用超时时间
 * 备注
 * @FeignClient("provider-service")定义调用的服务名称，注册在eureka-server上的服务
 *
 * fallbackFactory:开启熔断机制
 * 有了fallbackFactory，前面的必须加上name属性，如name="provider-service"
 *
 *
 * 下面的内容表示provider-service服务中暴露的rest接口路径，get请求，返回json内容
 *  @RequestMapping(value = "/provider-service/feign", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})
 *
 *  备注：模拟熔断机制，启动服务后，断开provider-service，看是否返回ICallFeignServiceFallbackFactory中定义的信息
 *  并且使用turbine-server进行链路的监控
 * @author Liebe
 */
@FeignClient(name="provider-service",fallbackFactory = ICallFeignServiceFallbackFactory.class)
public interface ICallFeignServiceClient {

    @RequestMapping(value = "/provider-service/feign", method = RequestMethod.GET, produces = {"application/json;charset=UTF-8"})

    /**
     * 定义调用的接口方法
     */
    String call();
}