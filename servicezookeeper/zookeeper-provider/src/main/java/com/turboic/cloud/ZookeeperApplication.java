package com.turboic.cloud;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * zookeeper-spring-cloud 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient

public class ZookeeperApplication
{
    private final static Logger logger = LoggerFactory.getLogger(ZookeeperApplication.class);
    public static void main( String[] args )
    {
        System.out.println( "Hello zookeeper application 服务提供者启动中!" );
        logger.info("spring cloud 集成zookeeper应用启动");
        new SpringApplicationBuilder(ZookeeperApplication.class).run(args);
    }
}
