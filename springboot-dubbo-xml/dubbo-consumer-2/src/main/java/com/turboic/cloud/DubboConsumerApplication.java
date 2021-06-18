package com.turboic.cloud;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:dubbo-consumer.xml"})
public class DubboConsumerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello dubbo consumer application 服务启动中!" );
        new SpringApplicationBuilder(DubboConsumerApplication.class).run(args);
    }
}
