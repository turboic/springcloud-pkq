package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 演示rabbitmq的基本功能
 * 启动方便，注释掉注册到Eureka-server服务
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class ServiceRabbitmqExampleApplication
{
    private static final Logger logger = LoggerFactory.getLogger(ServiceRabbitmqExampleApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "service-rabbitmq-example!" );
        logger.info("hello rabbitmq");
        new SpringApplicationBuilder(ServiceRabbitmqExampleApplication.class).run(args);
    }
}
