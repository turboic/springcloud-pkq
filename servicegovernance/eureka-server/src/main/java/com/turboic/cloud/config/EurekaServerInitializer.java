package com.turboic.cloud.config;

import com.turboic.cloud.EurekaServerApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

public class EurekaServerInitializer implements ApplicationContextInitializer {
    private static final Logger log = LoggerFactory.getLogger(EurekaServerInitializer.class);
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        log.info("初始化initialize应用的上下文对象配置");
        ConfigurableEnvironment environment =configurableApplicationContext.getEnvironment();
        configurableApplicationContext.registerShutdownHook();
        log.error("注册程序启动的狗子");
        boolean lookup  = AnnotationUtils.isCandidateClass(EurekaServerApplication.class, EnableEurekaServer.class);
        log.error("spring boot application enable eureka server {}",lookup);
        log.error("打印程序测试输入显示结果，看看是什么");
        //AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor;
    }
}
