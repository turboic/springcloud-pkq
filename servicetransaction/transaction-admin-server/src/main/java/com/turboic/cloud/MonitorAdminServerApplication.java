package com.turboic.cloud;
import com.turboic.cloud.dingding.DingDingNotifier;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
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
@Slf4j
public class MonitorAdminServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(MonitorAdminServerApplication.class);
    public static void main(String[] args) {
        logger.info("hello monitor spring-boot for admin-server 应用 微服务启动中");
        logger.info("分布式微服务使用Nacos注册中心");
        SpringApplication.run(MonitorAdminServerApplication.class, args);
    }
    @Bean
    public DingDingNotifier dingDingNotifier(InstanceRepository repository) {
        return new DingDingNotifier(repository);
    }
}
