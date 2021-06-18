package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableEurekaClient
public class SpringBootRocketMqConsumerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello rocketmq consumer 微服务启动中!" );
        new SpringApplicationBuilder(SpringBootRocketMqConsumerApplication.class).run(args);
    }

}
