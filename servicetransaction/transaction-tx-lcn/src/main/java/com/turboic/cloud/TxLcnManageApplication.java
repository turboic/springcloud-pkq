package com.turboic.cloud;
import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * transaction tx-lcn for spring-boot 应用
 * eureka-server和nacos不能共存
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@Slf4j
@EnableTransactionManagerServer
public class TxLcnManageApplication {
    private static final Logger logger = LoggerFactory.getLogger(TxLcnManageApplication.class);
    public static void main(String[] args) {
        logger.info("hello tx-lan 微服务启动中");
        logger.info("分布式微服务使用Nacos注册中心");
        SpringApplication.run(TxLcnManageApplication.class, args);
    }
}
