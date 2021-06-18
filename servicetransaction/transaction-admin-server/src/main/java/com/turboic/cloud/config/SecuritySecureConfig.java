package com.turboic.cloud.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author liebe
 */
@Configuration
public class SecuritySecureConfig extends WebSecurityConfigurerAdapter {
    private final String adminContextPath;

    /**
     * 出现找不到adminServerProperties类型的错误，是因为没有加上
     * 注解@EnableAdminServer
     * @param adminServerProperties
     */
    public SecuritySecureConfig(AdminServerProperties adminServerProperties) {
        this.adminContextPath = adminServerProperties.getContextPath();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(adminContextPath + "/");
        http.authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers(adminContextPath + "/assets/**").permitAll()
                .antMatchers(adminContextPath + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(adminContextPath + "/login").successHandler(successHandler).and()
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .httpBasic()
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .csrf()
                .ignoringRequestMatchers(
                        new AntPathRequestMatcher(this.adminContextPath + "/instances", HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/instances/*", HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(this.adminContextPath + "/actuator/**")
                ).disable();
    }
}
