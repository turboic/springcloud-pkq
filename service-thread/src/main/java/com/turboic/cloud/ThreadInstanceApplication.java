package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
/**
 * thread-演示程序
 *
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class ThreadInstanceApplication
{
    private static final Logger logger = LoggerFactory.getLogger(ThreadInstanceApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello thread SpringBoot集成启动开始!" );
        logger.debug("spring-boot + Thread 完整 程序启动");
        logger.info("Thread 的演示程序");
        new SpringApplicationBuilder(ThreadInstanceApplication.class).run(args);
    }
}
