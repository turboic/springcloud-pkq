package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * skywalking-provider 应用
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class GatewayApplication
{
    /****
     * 出现错误Parameter 0 of method modifyRequestBodyGatewayFilterFactory in org.springframework.cloud.gateway.config.GatewayAutoConfiguration required a bean of type
     * 'org.springframework.http.codec.ServerCodecConfigurer' that could not be found.
     * 依赖冲突，spring-cloud-starter-gateway与spring-boot-starter-web和spring-boot-starter-webflux依赖冲突
     *
     *解决：
     * 排除 spring-boot-starter-web和spring-boot-starter-webflux依赖
     *
     * <dependency>
     *     <groupId>org.springframework.cloud</groupId>
     *     <artifactId>spring-cloud-starter-gateway</artifactId>
     *     <exclusions>
     *         <exclusion>
     *             <groupId>org.springframework.boot</groupId>
     *             <artifactId>spring-boot-starter-web</artifactId>
     *         </exclusion>
     *         <exclusion>
     *             <groupId>org.springframework.boot</groupId>
     *             <artifactId>spring-boot-starter-webflux</artifactId>
     *         </exclusion>
     *     </exclusions>
     * </dependency>
     *
     *
     *
     * 访问网关端口查看路由信息
     * http://localhost:9527/actuator/gateway/routes
     *
     * @param args
     */
    public static void main( String[] args )
    {
        System.out.println( "Hello gateway entry!" );
        new SpringApplicationBuilder(GatewayApplication.class).run(args);
    }
}
