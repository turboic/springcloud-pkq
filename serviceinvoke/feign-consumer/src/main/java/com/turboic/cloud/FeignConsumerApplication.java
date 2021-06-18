package com.turboic.cloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * feign-consumer
 *
 * 服务消费者
 *
 * 注解@EnableFeignClients用来开启Feign功能
 *
 * 注解@EnableCircuitBreaker用来开启熔断效果
 *
 * 可以使用turbine-server来监控本服务信息
 *
 * @author liebe
 */

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableCircuitBreaker
public class FeignConsumerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "feign-consumer!" );
        new SpringApplicationBuilder(FeignConsumerApplication.class).run(args);
    }


    /***
     * turbine-server启动监控不成功的问题
     * @return
     */
    @Bean
    public ServletRegistrationBean getServletRegistrationBean(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }

}
