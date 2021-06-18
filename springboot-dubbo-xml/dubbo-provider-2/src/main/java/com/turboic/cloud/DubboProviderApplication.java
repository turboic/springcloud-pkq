package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ImportResource;
@SpringBootApplication
@ImportResource({"classpath:dubbo-provider.xml"})
public class DubboProviderApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello dubbo-provider-application 服务启动中!" );
        new SpringApplicationBuilder(DubboProviderApplication.class).run(args);
    }
}
