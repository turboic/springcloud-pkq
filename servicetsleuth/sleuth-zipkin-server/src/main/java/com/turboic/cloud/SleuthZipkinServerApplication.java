package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import zipkin2.server.internal.EnableZipkinServer;

/**
 * client spring-boot for sleuth-zipkin-server 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableZipkinServer
public class SleuthZipkinServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(SleuthZipkinServerApplication.class);
    public static void main(String[] args) {
        logger.info("hello client spring-boot for sleuth-zipkin-server应用 微服务启动中");
        logger.info("注册服务到alibaba nacos 上");
        SpringApplication.run(SleuthZipkinServerApplication.class, args);
    }
}
