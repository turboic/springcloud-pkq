package com.turboic.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author liebe
 */
@EnableEurekaServer
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
public class SpringBootEurekaServerApplication
{
    private static final Logger log = LoggerFactory.getLogger(SpringBootEurekaServerApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello World，正在启动Eureka-server服务的实例!" );
        SpringApplication application = new SpringApplicationBuilder(SpringBootEurekaServerApplication.class).application();
        application.setAllowBeanDefinitionOverriding(true);
        application.setHeadless(true);
        application.run(args);
        log.error("spring boot应用程序初始化了");
    }
}
