package com.turboic.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * eureka-client
 *
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class EurekaClientApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello Eureka Client!" );
        new SpringApplicationBuilder(EurekaClientApplication.class).run(args);
    }
}
