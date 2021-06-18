package com.turboic.cloud.request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 *
 *
 * 手动刷新功能（PostMan只能发送get请求操作）
 * http://localhost:8075/actuator/bus-refresh
 *
 * 使用rabbitmq-server自动刷新，实现了的修改的客户端的自动更新，稍微点的延迟，并且存在中文乱码的现象
 * 同时properties格式不标准，客户端的值是无法读取的
 */
@RestController
@RefreshScope
@RequestMapping("/config-bus-client")
public class GithubConfigBusClientController {
    private static final Logger logger = LoggerFactory.getLogger(GithubConfigBusClientController.class);

    @Value("${demo}")
    private String demo;

    /***
     * 实际值变了，未更新
     * 配置bus.id
     * @return
     */
    @RequestMapping("/bus")
    public String Pkq() {
        logger.info("GithubConfigBusClientController客户端刷新响应结果为:{}",this.demo);
        return this.demo;
    }
}
