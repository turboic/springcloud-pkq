package com.turboic.cloud.request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liebe
 */
@RestController
@RequestMapping("/gateway")
@Slf4j
public class DubboProviderController {

    @RequestMapping("/dubbo")
    public String dubbo(String name){
        log.info("gateway-dubbo微服务的DubboProviderController");
        return "晚上好，该睡觉了，哈哈哈哈哈哈 "+name;
    }
}
