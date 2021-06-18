package com.turboic.cloud;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * openfeign-starter-provider 应用
 * @author liebe
 *
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
public class OpenfeignStarterProviderApplication
{
    /***
     * 需要启动admin-server服务，作为服务监控的客户端admin-client
     * openfeign-starter-provider
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello openfeign-starter-provider 服务启动中!" );
        new SpringApplicationBuilder(OpenfeignStarterProviderApplication.class).run(args);
    }
    /***
     * spring-boot2.2中/actuator/hystrix.stream出现404的问题
     * @return
     */
    @Bean
    public ServletRegistrationBean getServlet(){
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/actuator/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
}
