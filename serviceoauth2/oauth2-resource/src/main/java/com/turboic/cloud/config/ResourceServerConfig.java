package com.turboic.cloud.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liebe
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);

    @Value("${spring.security.oauth2.client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.authorization.check-token-access}")
    private String checkTokenEndpointUrl;

    private final RedisConnectionFactory redisConnectionFactory;

    public ResourceServerConfig(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }
    //@Bean("redisTokenStore")
    public TokenStore redisTokenStore(){
        logger.error("创建Bean----------TokenStore RedisTokenStore");
        return new RedisTokenStore(redisConnectionFactory);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        return builder.build();
    }

    @Bean
    public AccessTokenConverter accessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }


    /***
     * 由于本项目资源服务和认证服务不在同一服务上，这里token‘认证使用远程认证服务器认证
     * @return
     */
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        logger.error("RemoteTokenServices服务,clientId:{},clientSecret:{}",clientId,clientSecret);
        logger.error("RemoteTokenServices服务,checkTokenEndpointUrl:{},",checkTokenEndpointUrl);
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setClientId(clientId);
        remoteTokenServices.setClientSecret(clientSecret);
        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        remoteTokenServices.setRestTemplate(restTemplate());
        remoteTokenServices.setAccessTokenConverter(accessTokenConverter());
        return remoteTokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources){
        try {
            super.configure(resources);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("public void configure(ResourceServerSecurityConfigurer resources)"+e);
        }
        logger.error("resources.tokenServices(remoteTokenServices()");
        resources.tokenServices(remoteTokenServices());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login/auth","/resource/getToken/**").permitAll().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
        //http.exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint()) ;
    }
}