package com.turboic.cloud;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * client spring-boot for dubbo-sleuth-provider 应用
 *  关于dubbo的provider实例应用，生产提供者，可以部署多个实例
 *  部署方法，先试用java -jar 的方式启动provider jar 文件
 *  本地修改server:
 *   port: 端口为另一个值
 *   因为是设置的轮训负载均衡策略
 *   那么consume消费端调用的时候将切换显示provider返回的两个不同的内容
 *
 *   停止其中一个provider实例，将会出现负载均衡的效果
 *   这样可以实现了动态扩容dubbo provider实例
 *   启动新的dubbo provider实例，
 *   将会注册到nacos服务列表中去
 *
 *   如果所有的provider 实例宕机，将考虑熔断机制，执行熔断器
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableDubbo
@EnableHystrix
@EnableTransactionManagement
@MapperScan("com.turboic.cloud.mapper")
public class SleuthProviderApplication {
    private static final Logger logger = LoggerFactory.getLogger(SleuthProviderApplication.class);
    public static void main(String[] args) {
        logger.info("hello client spring-boot for dubbo-sleuth-provider应用 微服务启动中");
        logger.info("注册服务到alibaba nacos 上");
        SpringApplication.run(SleuthProviderApplication.class, args);
    }
}
