package com.turboic.cloud;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * client spring-boot for service-provider 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
public class ClientServiceProviderApplication {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceProviderApplication.class);
    public static void main(String[] args) {
        logger.info("hello client spring-boot for service-provider 应用 微服务启动中");
        logger.info("注册服务到alibaba nacos 上");
        SpringApplication.run(ClientServiceProviderApplication.class, args);
    }
}
