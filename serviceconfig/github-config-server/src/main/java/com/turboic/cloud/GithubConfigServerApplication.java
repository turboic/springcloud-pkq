package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * github-config
 * 注解@EnableConfigServer开启配置服务
 *
 * 访问地址
 * http://localhost:8073/springcloud-config/dev
 *
 * http://localhost:8073/mygirl/dev
 *
 * @author liebe
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableConfigServer
public class GithubConfigServerApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello GithubConfig Server!" );
        new SpringApplicationBuilder(GithubConfigServerApplication.class).run(args);
    }
}
