package com.turboic.cloud;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableDubbo
public class SentinelServiceProviderApplication
{
    private static final Logger log = LoggerFactory.getLogger(SentinelServiceProviderApplication.class);
    public static void main( String[] args )
    {
        log.error( "Hello sentinel nachos provider 微服务启动中!" );
        new SpringApplicationBuilder(SentinelServiceProviderApplication.class).run(args);
    }
}
