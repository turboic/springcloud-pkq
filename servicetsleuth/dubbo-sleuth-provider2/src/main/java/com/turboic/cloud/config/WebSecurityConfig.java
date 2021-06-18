/*
package com.turboic.cloud.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

*/
/**
 * @author liebe
 *//*

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/druid").hasRole("ADMIN")
                .antMatchers("/provider/**").hasRole("ADMIN").and()
                //.csrf().disable() //关闭CSRF
                .csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
            @Override
            public boolean matches(HttpServletRequest httpServletRequest) {
                String servletPath = httpServletRequest.getServletPath();
                return !servletPath.contains("/druid") && !servletPath.contains("/provider") && !servletPath.contains("/login");
            }
        }).and().formLogin().loginPage("/login").defaultSuccessUrl("/hello").and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("12345").roles("ADMIN");
    }
}*/
