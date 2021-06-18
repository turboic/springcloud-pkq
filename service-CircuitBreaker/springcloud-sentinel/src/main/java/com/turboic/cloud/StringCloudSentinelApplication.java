package com.turboic.cloud;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * http://localhost:8081/actuator/sentinel
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.turboic.cloud.openfeign")
@EnableDubbo
public class StringCloudSentinelApplication
{
    private static final Logger log = LoggerFactory.getLogger(StringCloudSentinelApplication.class);

    /***
     * transaction for mybatis
     * @param args
     */
    public static void main( String[] args )
    {
        log.error( "Hello transaction for mybatis 服务启动中!" );
        new SpringApplicationBuilder(StringCloudSentinelApplication.class).run(args);
    }
}
