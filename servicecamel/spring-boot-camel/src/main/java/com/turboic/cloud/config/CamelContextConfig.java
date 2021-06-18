package com.turboic.cloud.config;
import com.turboic.cloud.example.RestRoute;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liebe
 */
@Configuration
public class CamelContextConfig {
    private static final Logger logger = LoggerFactory.getLogger(CamelContextConfig.class);
    @Bean
    public CamelContext getCamelContext(){
        logger.error("开始配置camelContext上下文");
        CamelContext camelContext = new DefaultCamelContext();
        try {
            camelContext.addRoutes(new RestRoute());
        } catch (Exception e) {
            logger.error("CamelContext添加路由异常:{}",e);
        }
        camelContext.setTracing(true);
        camelContext.start();
        logger.error("camelContext上下文开始启动");
        return camelContext;
    }
}
