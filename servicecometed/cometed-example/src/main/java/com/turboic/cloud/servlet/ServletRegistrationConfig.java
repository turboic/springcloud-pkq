package com.turboic.cloud.servlet;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author libe
 */
@Configuration
public class ServletRegistrationConfig {
    @Bean
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean bean=new ServletRegistrationBean(new StandardServlet());
        bean.addUrlMappings("/standard");
        return bean;
    }
}
