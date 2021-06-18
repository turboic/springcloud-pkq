package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * rocketmq-starter-reference 应用
 * @author liebe
 *
 * 消费端重启服务重负消费的问题
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
public class RocketMqStarterReferenceApplication
{
    /***
     * rocketmq-starter-reference
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello rocketmq-starter-reference 服务启动中!" );
        new SpringApplicationBuilder(RocketMqStarterReferenceApplication.class).run(args);
    }
}
