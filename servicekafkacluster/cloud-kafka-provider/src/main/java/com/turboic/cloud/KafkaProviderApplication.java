package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * cloud-stream-kafka-provider
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
        logger.info("cloud-stream-kafka-provider 微服务启动成功");
        new SpringApplicationBuilder(KafkaProviderApplication.class).run(args);
    }
}