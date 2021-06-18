package com.turboic.cloud.request;
import com.turboic.cloud.service.ProviderServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liebe
 */

@RestController
@RequestMapping("/provider-service")
public class ProviderServiceController {

    private final ProviderServiceService providerServiceService;

    @Autowired
    public ProviderServiceController(ProviderServiceService providerServiceService) {
        this.providerServiceService = providerServiceService;
    }

    @RequestMapping("/call")
    public String call(String name){
        return providerServiceService.sayHello(name);
    }


    /**
     * 创建Rest接口，为feign-consumer消费服务提供使用
     * @return
     */
    @GetMapping("/feign")
    public Map feignInvoke(String feign){
        Map<String,String> map = new HashMap<>();
        map.put("spring.application.name","provider-service");
        map.put("version","1.0.0");
        map.put("consumer","feign");
        map.put("注册中心","eureka-server");
        map.put("author","liebe");
        map.put("feign",feign);
        return map;
    }

    @RequestMapping("/ribbon")
    public Map ribbon(String name){
        Map<String,String> ribbon = new HashMap<>();
        ribbon.put("spring.application.name","provider-service");
        ribbon.put("path","/ribbon");
        ribbon.put("version","1.0.0");
        ribbon.put("consumer","ribbon");
        ribbon.put("注册中心","eureka-server");
        ribbon.put("author","liebe");
        ribbon.put("name",name);
        return ribbon;
    }
}
