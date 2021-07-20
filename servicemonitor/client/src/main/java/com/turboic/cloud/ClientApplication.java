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
public class ClientApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello Client!" );
        new SpringApplicationBuilder(ClientApplication.class).run(args);
    }
}
