package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
//@EnableDiscoveryClient
//@EnableEurekaClient
public class SpringBootRocketMqProducerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello rocketmq producer 微服务启动中!" );
        new SpringApplicationBuilder(SpringBootRocketMqProducerApplication.class).run(args);
    }

}
