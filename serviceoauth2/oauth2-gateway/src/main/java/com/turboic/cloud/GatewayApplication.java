package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * skywalking-provider 应用
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class GatewayApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello gateway entry!" );
        new SpringApplicationBuilder(GatewayApplication.class).run(args);
    }
}
