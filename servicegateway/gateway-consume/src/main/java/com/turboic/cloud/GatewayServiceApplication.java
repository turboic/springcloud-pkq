package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * Gateway-service 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
public class GatewayServiceApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello Gateway service!" );
        new SpringApplicationBuilder(GatewayServiceApplication.class).run(args);
    }
}
