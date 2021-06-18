package com.turboic.cloud.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
/**
 * @author liebe
 */
@Component
@Configuration
public class WebServerStartupInfo implements ApplicationListener<WebServerInitializedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(WebServerStartupInfo.class);
    private int serverPort;
    @Autowired
    private Environment environment;
    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event) {
        logger.error("1.当前WebServer实现类为："+event.getWebServer().getClass().getName());
        WebServerApplicationContext applicationContext = event.getApplicationContext();
        int port = applicationContext.getWebServer().getPort();
        if (this.serverPort == 0){
            this.serverPort = port;
        }
        String name = applicationContext.getWebServer().getClass().getName();
        logger.error("1.容器端口"+port+",实现类-"+name);
    }

    /**
     * 在spring boot应用启动后回调
     * @param context
     * @return
     */

    @Bean
    public ApplicationRunner runner(WebServerApplicationContext context) {
        return args -> {
            logger.error("2.当前WebServer实现类为ApplicationRunner："+context.getWebServer().getClass().getName());
        };
    }

    public String getHostAddress() {
        InetAddress address = null;
        try {
            address = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        }
        String url = address.getHostAddress();
        logger.error("spring-boot程序启动的HostAddress是:{}",url);
        return url;
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        logger.error("程序启动端口是:{}",event.getWebServer().getPort());
        String [] activeProfiles = environment.getActiveProfiles();
        Arrays.stream(activeProfiles).forEach(a->{
            logger.error(a);
        });
        String [] defaultProfiles = environment.getDefaultProfiles();
        Arrays.stream(defaultProfiles).forEach(d->{
            logger.error(d);
        });
        this.serverPort = event.getWebServer().getPort();
    }

    public int getServerPort() {
        return serverPort;
    }
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
}