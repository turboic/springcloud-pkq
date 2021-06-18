package com.turboic.cloud;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * zookeeper-spring-cloud 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2

public class Oauth2AuthServerApplication
{
    private final static Log log = LogFactory.getLog(Oauth2AuthServerApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello spring cloud 集成oauth2服务认证应用启动!" );
        log.info("spring cloud 集成oauth2服务认证应用启动");
        new SpringApplicationBuilder(Oauth2AuthServerApplication.class).run(args);
    }
}
