package com.turboic.cloud;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * @author liebe
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAdminServer
public class SpringBootAdminServerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Admin Server Eureka Startup!" );
        new SpringApplicationBuilder(SpringBootAdminServerApplication.class).run(args);
    }
}
