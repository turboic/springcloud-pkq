package com.turboic.cloud.request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author liebe
 *
 * 服务的消费者，根据服务名称进行调用
 */

@RestController
public class RibbonConsumerController {
    final RestTemplate restTemplate;

    @Autowired
    public RibbonConsumerController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
    public String helloConsumer() {
        return restTemplate.getForEntity("http://provider-service/provider-service/call",String.class).getBody();
    }

}
