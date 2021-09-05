package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
/**
 * @author liebe
 */
@SpringBootApplication
public class PulsarApplication {
    private static final Logger logger = LoggerFactory.getLogger(PulsarApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello camel application startup ...!" );

        logger.error("启动微服务spring-boot-pulsar程序");

        new SpringApplicationBuilder(PulsarApplication.class).run(args);
    }
}
