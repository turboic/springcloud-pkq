package com.turboic.cloud.config;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liebe
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }


    @Bean
    public ServletRegistrationBean<StatViewServlet> servletRegistrationBean() {
        ServletRegistrationBean<StatViewServlet> servletServletRegistrationBean =
                new ServletRegistrationBean<>();
        servletServletRegistrationBean.setServlet(new StatViewServlet());
        servletServletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParas = new HashMap<>(200);
        servletServletRegistrationBean.setOrder(1);
        initParas.put("loginUsername", "admin");
        initParas.put("loginPassword", "123456");
        initParas.put("allow","localhost");

        //initParas.put("deny","");

        initParas.put("resetEnable", "true");
        servletServletRegistrationBean.setInitParameters(initParas);
        return servletServletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<WebStatFilter> filterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<WebStatFilter>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        Map<String, String> initParams = new HashMap<>(200);
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif," +
                "*.jpg,*.png, *.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        return filterRegistrationBean;
    }
}