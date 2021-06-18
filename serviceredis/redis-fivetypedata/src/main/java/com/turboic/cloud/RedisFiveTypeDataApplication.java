package com.turboic.cloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * redis crud five data type 应用
 * @author liebe
 *
 * 备注：dubbo服务消费者，应该在provider服务启动注册成功之后，
 * 再启动
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
@Slf4j
public class RedisFiveTypeDataApplication
{
    /***
     * redis crud five data type
     * @param args
     */
    public static void main( String[] args )
    {
        log.info( "Hello redis crud five data type 服务启动中!" );
        new SpringApplicationBuilder(RedisFiveTypeDataApplication.class).run(args);
    }
}
