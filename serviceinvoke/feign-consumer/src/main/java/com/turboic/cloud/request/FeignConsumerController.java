package com.turboic.cloud.request;
import com.turboic.cloud.feign.ICallFeignServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liebe
 *
 * 服务的消费者，根据服务名称进行调用
 *
 *
 */

@RestController
public class FeignConsumerController {

    private final ICallFeignServiceClient iCallFeignServiceClient;

    @Autowired
    public FeignConsumerController(ICallFeignServiceClient iCallFeignServiceClient) {
        this.iCallFeignServiceClient = iCallFeignServiceClient;
    }


    /**
     * iCallFeignServiceClient相当于service接口了
     * 在iCallFeignServiceClient绑定相关的调用服务信息
     * @return
     */
    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String feignConsumer() {
       return iCallFeignServiceClient.call();
    }
}
