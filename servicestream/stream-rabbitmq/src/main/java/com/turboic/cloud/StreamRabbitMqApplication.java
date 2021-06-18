package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * stream-rabbitmq
 * 启动方便，注释掉注册到Eureka-server服务
 * @author liebe
 */
@SpringBootApplication
public class StreamRabbitMqApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello stream rabbitmq!" );
        new SpringApplicationBuilder(StreamRabbitMqApplication.class).run(args);
    }
}
