package com.turboic.cloud;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * client spring-boot for dubbo-sleuth-consume 应用
 * @EnableHystrix表示开启熔断机制
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableDubbo
@EnableHystrix
public class DubboSleuthConsumeApplication {
    private static final Logger logger = LoggerFactory.getLogger(DubboSleuthConsumeApplication.class);
    public static void main(String[] args) {
        logger.info("hello client spring-boot for dubbo-sleuth-consume  微服务启动中");
        logger.info("注册服务到alibaba nacos 上");
        SpringApplication.run(DubboSleuthConsumeApplication.class, args);
    }
}
