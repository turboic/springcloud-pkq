package com.turboic.cloud.example;

import com.turboic.cloud.config.MyProcess;
import com.turboic.cloud.config.WebServerStartupInfo;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author liebe
 */
@Component
public class RestRoute extends RouteBuilder {
    private static final Logger logger = LoggerFactory.getLogger(RestRoute.class);
    private static final int port = 80;

    @Autowired
    WebServerStartupInfo webServerStartupInfo;

    public RestRoute() {
    }

    @Override
    public void configure() throws Exception {
        if(webServerStartupInfo != null){
            int defaultPort = webServerStartupInfo.getServerPort();
            if(defaultPort<0 || defaultPort == 0 || defaultPort == port){
                throw new IllegalArgumentException("程序启动端口异常");
            }
            restConfiguration().host(webServerStartupInfo.getHostAddress()).port(webServerStartupInfo.getServerPort());
        }
        else{
            String host = "127.0.0.1";
            int port =9892;
            try{
                restConfiguration().host(host).port(port);
            }catch(Exception e){
                log.error("restConfiguration-:{}-:{}-:{}",host,port,e);
            }

        }


        logger.error("路由开始转发操作");
        from("timer:hello?period=30000")
                .setHeader("id", simple("${random(1,3)}"))
                .process(new MyProcess())
                .to("rest:get:pets/{id}")
                .log("${body}");
    }
}