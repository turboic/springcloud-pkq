package com.turboic.cloud;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * monitor spring-boot for admin-server 应用
 * eureka-server和nacos不能共存
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableAdminServer
public class SleuthServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(SleuthServiceApplication.class);
    public static void main(String[] args) {
        logger.info("hello monitor spring-boot for admin-server 应用 微服务启动中");
        logger.info("分布式微服务使用Nacos注册中心");
        SpringApplication.run(SleuthServiceApplication.class, args);
    }
}
