package com.turboic.cloud.request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liebe
 */
@RestController
@RequestMapping("/skywalking")
public class SkywalkingProviderController {
    private static final Logger logger = LoggerFactory.getLogger(SkywalkingProviderController.class);

    @PostMapping("/provider")
    public String provider(String name){
        return "晚上好，该睡觉了，哈哈哈哈哈哈 "+name;
    }
}
