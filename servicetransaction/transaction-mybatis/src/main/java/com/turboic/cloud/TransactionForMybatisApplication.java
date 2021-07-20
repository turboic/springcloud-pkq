package com.turboic.cloud;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * transaction for mybatis 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
//@EnableDiscoveryClient
//@EnableEurekaClient
@EnableDubbo
@Slf4j
public class TransactionForMybatisApplication
{
    /***
     * transaction for mybatis
     * @param args
     */
    public static void main( String[] args )
    {
        log.error( "Hello transaction for mybatis 服务启动中!" );
        new SpringApplicationBuilder(TransactionForMybatisApplication.class).run(args);
    }
}
