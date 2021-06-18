package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class FastDfsClientApplication{
    private static final Logger logger = LoggerFactory.getLogger(FastDfsClientApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello fastdfs-client 集成启动开始!" );
        logger.debug("spring-boot + fastdfs-client 完整 程序启动");
        new SpringApplicationBuilder(FastDfsClientApplication.class).run(args);
    }
}
