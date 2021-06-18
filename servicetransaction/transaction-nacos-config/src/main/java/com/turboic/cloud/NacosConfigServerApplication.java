package com.turboic.cloud;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * monitor spring-boot for admin-server 应用
 * eureka-server和nacos不能共存
 *
 *http访问地址
 * http://127.0.0.1:9605/actuator/nacos-config
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@Slf4j
public class NacosConfigServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(NacosConfigServerApplication.class);
    public static void main(String[] args) {
        logger.info("hello  spring-boot for nacos-config 应用 微服务启动中");
        logger.info("使用nacos服务治理和服务配置");
        SpringApplication.run(NacosConfigServerApplication.class, args);
    }
}
