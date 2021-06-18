package com.turboic.cloud;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * dubbo-starter-reference 应用
 * @author liebe
 *
 * 备注：dubbo服务消费者，应该在provider服务启动注册成功之后，
 * 再启动
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
@EnableDubbo
public class DubboStarterReferenceApplication
{
    /***
     * sdubbo-starter-reference
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello dubbo-starter-reference 服务启动中!" );
        new SpringApplicationBuilder(DubboStarterReferenceApplication.class).run(args);
    }
}
