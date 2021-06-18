package com.turboic.cloud.request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liebe
 *
 * 用于服务配置的自动刷新功能
 * 访问地址
 *
 *http://localhost:8073/github/config
 *
 * 手动刷新功能（PostMan只能发送Post请求操作）
 * http://localhost:8074/actuator/refresh
 */
@RestController
@RefreshScope
@RequestMapping("/config-client")
public class GithubConfigClientController {

    @Value("${demo}")
    private String demo;

    @RequestMapping("/test")
    public String myGirl() {
        return this.demo;
    }
}
