package com.turboic.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * elk-演示程序
 * @author liebe
 */
@SpringBootApplication
public class ElasticStackInstanceApplication
{
    private static final Logger logger = LoggerFactory.getLogger(ElasticStackInstanceApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello elk 客户端非SpringBootStarter集成启动开始!" );
        logger.debug("spring-boot + elk 完整 程序启动");
        logger.info("elk 的演示程序");
        new SpringApplicationBuilder(ElasticStackInstanceApplication.class).run(args);
    }
}
