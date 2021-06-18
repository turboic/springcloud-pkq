package com.turboic.cloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * ribbon-consumer
 *
 * 服务消费者
 *
 * @author liebe
 */

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class RibbonConsumerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "ribbon-consumer!" );
        new SpringApplicationBuilder(RibbonConsumerApplication.class).run(args);
    }

    /***
     * 创建负载均衡
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
