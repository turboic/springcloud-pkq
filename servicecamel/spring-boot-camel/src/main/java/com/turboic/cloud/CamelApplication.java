package com.turboic.cloud;
import com.turboic.cloud.example.RestRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * camel-application
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient

public class CamelApplication {
    private static final Logger logger = LoggerFactory.getLogger(CamelApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello camel application startup ...!" );
        logger.error("启动微服务spring-boot-camel程序");

        new SpringApplicationBuilder(CamelApplication.class).run(args);
    }
}
