package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * rocketmq-starter-provider 应用
 * @author liebe
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
public class RocketMqStarterProviderApplication
{
    /***
     * rocketmq-starter-provider
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello rocketmq-starter-provider 服务启动中!" );
        new SpringApplicationBuilder(RocketMqStarterProviderApplication.class).run(args);
    }

}
