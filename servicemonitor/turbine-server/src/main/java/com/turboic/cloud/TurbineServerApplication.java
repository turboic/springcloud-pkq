package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

/**
 * TurbineServer服务
 * 端口为8095
 * 页面访问的地址为
 *
 * 仪表盘和监控集群
 *
 * 访问，出现图形用户界面的小熊猫
 * http://localhost:8095/hystrix
 *
 * http://localhost:8095/turbine.stream
 *
 * 注意：我们在第一次进入时画面显示为loading...，只有在访问了接口之后才会出现上面的画面，监控台上的控制信息详细说明可以网上查找资料……^ _ ^
 *
 * 不停的ping
 * http://localhost:8095/actuator/hystrix.stream
 *
 * 不停的ping
 *http://localhost:8095/turbine.stream?cluster=default
 *
 * 很棒啦，最新的版本2.2.2支持中文啦
 * 作为eureka-server的注册客户端使用的
 *
 * @author liebe
 *
 *
 * 备注：一直出现Load的问题
 */

@SpringBootApplication
@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
@EnableTurbine
@EnableCircuitBreaker
public class TurbineServerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Turbine Server!" );
        new SpringApplicationBuilder(TurbineServerApplication.class).run(args);
    }


}
