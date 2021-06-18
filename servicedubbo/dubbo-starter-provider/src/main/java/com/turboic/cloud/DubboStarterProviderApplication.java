package com.turboic.cloud;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * dubbo-starter-provider 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
@EnableDubbo
public class DubboStarterProviderApplication
{
    /***
     * sdubbo-starter-provider
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello dubbo-starter-provider 服务启动中!" );
        new SpringApplicationBuilder(DubboStarterProviderApplication.class).run(args);
    }


}
