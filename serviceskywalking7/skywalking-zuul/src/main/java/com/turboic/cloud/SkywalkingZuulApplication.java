package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 * skywalking-zuul 应用
 * 旨在配置动态的路由系统
 * @EnableZuulProxy开启zuul功能
 * @author liebe
 */

/***
 * 浏览器查看路由信息
 * http://localhost:9860/actuator/routes
 */

/***
 * 访问订外卖的地址
 * 其中zuul-api/skywalking-wo表示path路径
 * skywalking-wo/dingwaimai表示serviceId为skywalking-wo的Controller的mvc地址
 * http://localhost:9860/zuul-api/skywalking-wo/skywalking-wo/dingwaimai
 */

/***
 * 出现错误
 * Load balancer does not have available server for client
 * application.yml中添加配置
 * ribbon:
 *   eureka:
 *     enable: true
 */
@SpringBootApplication
@EnableSwagger2
@EnableDiscoveryClient
@EnableEurekaClient
@EnableZuulProxy
public class SkywalkingZuulApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello skywalking zuul!" );
        new SpringApplicationBuilder(SkywalkingZuulApplication.class).run(args);
    }
}
