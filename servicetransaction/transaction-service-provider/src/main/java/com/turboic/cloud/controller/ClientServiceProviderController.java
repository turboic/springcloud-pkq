package com.turboic.cloud.controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 日志测试
 * @author liebe
 * 访问地址
 * http://192.168.124.6:9602/clientServiceProvider
 */
@RestController
public class ClientServiceProviderController {
    @RequestMapping("/clientServiceProvider")
    public String clientServiceProvider(){
        return "ClientServiceProvider";
    }
}
