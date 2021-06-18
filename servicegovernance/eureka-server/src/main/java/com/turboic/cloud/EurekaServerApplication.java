package com.turboic.cloud;
import com.turboic.cloud.config.EurekaServerInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

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
    private static final Logger log = LoggerFactory.getLogger(EurekaServerApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello World，正在启动Eureka-server服务的实例!" );
        /*ConfigurableApplicationContext configurableApplicationContext =
                new SpringApplicationBuilder(EurekaServerApplication.class).run(args);*/
        SpringApplication application = new SpringApplicationBuilder(EurekaServerApplication.class).application();
        application.addInitializers(new EurekaServerInitializer());
        ClassLoader classLoader = application.getClassLoader();
        application.setAllowBeanDefinitionOverriding(true);
        application.setHeadless(true);
        application.run(args);
        log.error("spring boot应用程序初始化了");
    }
}
