package com.turboic.cloud;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * feign-consumer
 *
 * 服务消费者
 *
 * 注解@EnableFeignClients用来开启Feign功能
 *
 * 注解@EnableCircuitBreaker用来开启熔断效果，开启断路器
 *
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 *
 * 可以使用turbine-server来监控本服务信息
 *  turbine 用来监控集群的功能
 * @author liebe
 */


/**
 * 加入服务链路追踪
 * @author liebe
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableHystrix
@EnableTurbine
@Slf4j
public class CircuitBreakerCommandApplication
{
    public static void main( String[] args )
    {
        System.out.println( "circuit-breaker-command!" );
        log.info( "circuit-breaker-command 服务启动中!" );
        new SpringApplicationBuilder(CircuitBreakerCommandApplication.class).run(args);
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
