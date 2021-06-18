package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * zipkin-server 应用
 * @author liebe
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
public class Zipkin2ServerConsumeApplication
{
    /***
     * spring-boot高版本不支持集成zipkin-server，此处单独运行jar文件
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello zipkin2 server for consume 服务消费者启动中!" );
        new SpringApplicationBuilder(Zipkin2ServerConsumeApplication.class).run(args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
