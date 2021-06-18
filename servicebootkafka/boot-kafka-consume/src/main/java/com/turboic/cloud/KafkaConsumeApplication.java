package com.turboic.cloud;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * kafka-consume服务
 *
 * @author liebe
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@Slf4j
@MapperScan("com.turboic.cloud.mapper.**")
public class KafkaConsumeApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello kafka-consume!" );
        log.error("kafka consume服务启动");
        new SpringApplicationBuilder(KafkaConsumeApplication.class).run(args);
    }
}
