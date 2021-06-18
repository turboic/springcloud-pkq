package com.turboic.cloud.feign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/***
 * @author Liebe
 */
@FeignClient(name="skywalking-qishou",fallbackFactory = IFeignDeliveryServiceFallbackFactory.class)
public interface IFeignDeliveryServiceClient {
    @RequestMapping(value = "/person/delivery", method = RequestMethod.POST)
    /**
     * 配送服务，订单号
     */
    String delivery(@RequestParam(value = "orderNumber") String orderNumber);
}