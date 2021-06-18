package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * service-rabbitmq-consume
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class ServiceRabbitmqConsumeApplication
{
    private static final Logger logger = LoggerFactory.getLogger(ServiceRabbitmqConsumeApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello service-rabbitmq-consume!" );
        new SpringApplicationBuilder(ServiceRabbitmqConsumeApplication.class).run(args);
    }
}
