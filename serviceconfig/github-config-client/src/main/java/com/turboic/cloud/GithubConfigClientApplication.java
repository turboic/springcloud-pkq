package com.turboic.cloud;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * github-config-client
 * 从config-server拉取配置信息
 *
 * 控制太出现信息Fetching config from server at : http://127.0.0.1:8073/
 *
 * 客户端的pom导入一定要注意啊
 *      <dependency>
 *       <groupId>org.springframework.cloud</groupId>
 *       <artifactId>spring-cloud-config-client</artifactId>
 *     </dependency>
 *
 * @author liebe
 *
 *
 * 备注：
 * cloud:
 *     config:
 *       label: master
 *       profile: test
 *       #uri: http://localhost:8073
 *       discovery:
 *         enabled: true
 *         #通过服务名称访问
 *         service-id: github-config-server
 *       allow-override: true
 *       enabled: true
 *       fail-fast: true
 *       server:
 *         git:
 *           bootstrap: true
 *           uri: https://github.com/turboic/repotest.git
 *       #定义要读取的资源文件名称前缀
 *       #下面的name属性不设置，获取值显示结果就为${demo}，而不是实际对应的名称
 *       name: springcloud-config
 *       上面这个name属性是个坑
 *       下面#Bean也是个坑
 *
 *
 *
 *
 *
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
public class GithubConfigClientApplication
{
    public static void main( String[] args )
    {
        System.out.println( "Hello GithubConfig Client!" );
        new SpringApplicationBuilder(GithubConfigClientApplication.class).run(args);
    }

    /**
     * 加上下面的的内容，程序启动不出现先@Value找不到的现象，但是取值${demo}显示原生的
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }
}
