package com.turboic.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * provider-service
 *
 * 服务提提供者
 *
 * @author liebe
 */

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ProviderServiceApplication
{
    public static void main( String[] args )
    {
        System.out.println( "provider-service!" );
        new SpringApplicationBuilder(ProviderServiceApplication.class).run(args);
    }
}
