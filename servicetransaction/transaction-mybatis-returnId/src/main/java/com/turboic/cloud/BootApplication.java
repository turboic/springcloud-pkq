package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * transaction for mybatis 应用
 * @author liebe
 */
@SpringBootApplication
public class BootApplication
{
    private static final Logger log = LoggerFactory.getLogger(BootApplication.class);
    public static void main( String[] args )
    {
        log.error( "Hello BootApplication for mybatis 服务启动中!" );
        new SpringApplicationBuilder(BootApplication.class).run(args);
    }
}
