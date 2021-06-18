package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
/**
 * Eureka-Server服务
 * 端口为8081
 * 服务治理和服务容错及服务监控等等
 *
 * @author liebe
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World，正在启动Eureka-server服务的实例!" );
        new SpringApplicationBuilder(EurekaServerApplication.class).run(args);
    }
}
