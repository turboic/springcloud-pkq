package com.turboic.cloud;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * admin-server服务
 * 端口为5000
 * 页面访问的地址为
 * http://localhost:5000
 * 很棒啦，最新的版本2.2.2支持中文啦
 * 作为eureka-server的注册客户端使用的
 *
 * 备注：在程序启动类加上@EnableTurbine注解开启Turbine功能,@EnableHystrix
 * 开启Hystrix功能，@EnableHystrixDashboard开启Hystrix Dashboard的功能。
 *
 * @author liebe
 *
 *
 * 备注：点击客户端服务，出现404的问题
 */

@SpringBootApplication
@EnableEurekaClient
@EnableAdminServer
@EnableHystrix
@EnableHystrixDashboard
@EnableTurbine
public class AdminServerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Admin Server Hystrix HystrixDashboard Turbine!" );
        new SpringApplicationBuilder(AdminServerApplication.class).run(args);
    }
}
