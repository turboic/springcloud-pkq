package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * kafka-provider
 *
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class KafkaProviderApplication
{
    private static final Logger logger = LoggerFactory.getLogger(KafkaProviderApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello kafka-provider!" );
        logger.debug("spring-boot kafka provider 程序启动");
        logger.info("kafka 的服务提供者启动");
        new SpringApplicationBuilder(KafkaProviderApplication.class).run(args);
    }
}
