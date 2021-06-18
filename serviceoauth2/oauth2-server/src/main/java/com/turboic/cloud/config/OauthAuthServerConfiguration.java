package com.turboic.cloud.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author libe
 * Oauth2-server授权认证配置
 */
@EnableAuthorizationServer
@Configuration

public class OauthAuthServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private static final String client = "app2";
    private static final String secret = "secret2";


    private static final String app_client = "baidu";
    private static final String app_secret = "baidu";

    private static final String resource_client = "resourceApp";
    private static final String resource_secret = "resourceApp";

    private final static int accessTokenValiditySeconds = 7200;

    private final static String redirectUris = "http://localhost:5020/client/oauth2";

    private final static String BAIDU_URL = "http://www.baidu.com";

    private final static String RESOURCE_CLIENT_URL = "http://localhost:3003/login/auth";

    private final static String [] authorizedGrantTypes = {"password", "implicit", "refresh_token", "authorization_code"};



    private final static Log log = LogFactory.getLog(OauthAuthServerConfiguration.class);
    public OauthAuthServerConfiguration() {
        super();
        log.info("OauthAuthServerConfiguration配置类初始化完成");
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        log.info("AuthorizationServerSecurityConfigurer configure");
        security.tokenKeyAccess("permitAll()")
                // 验证获取Token的验证信息
                //.checkTokenAccess("isAuthenticated()")
                //http://localhost:9969/oauth/check_token登录认证出现了401
                .checkTokenAccess("permitAll()")
                //这个如果配置支持allowFormAuthenticationForClients的，且对/oauth/token请求的参数中有client_id和client-secret的会走ClientCredentialsTokenEndpointFilter来保护
                //如果没有支持allowFormAuthenticationForClients或者有支持但对/oauth/token请求的参数中没有client_id和client_secret的，走basic认证保护
                .passwordEncoder(new BCryptPasswordEncoder())
                .allowFormAuthenticationForClients();
    }

    /**
     * error="invalid_request", error_description="At least one redirect_uri must be registered with the client."
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        log.info("执行ClientDetailsServiceConfigurer configure");
        super.configure(clients);
        clients.inMemory().withClient(client)
                .secret(passwordEncoder().encode(secret))
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(accessTokenValiditySeconds)
                .authorizedGrantTypes(authorizedGrantTypes)
                //.redirectUris(redirectUris)
                //自动授权配置
                //.autoApprove(true)
                .authorities("admin","ROLE_ADMIN")
                .scopes("all", "read", "write")

                //另一个客户端的应用配置
                .and()
                .withClient(app_client)
                .secret(passwordEncoder().encode(app_secret))
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(accessTokenValiditySeconds)
                .authorizedGrantTypes(authorizedGrantTypes)
                .redirectUris(BAIDU_URL)
                .authorities("admin","ROLE_ADMIN")
                .scopes("all", "read", "write")

                //另一个客户端的应用配置
                .and()
                .withClient(resource_client)
                .secret(passwordEncoder().encode(resource_secret))
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(accessTokenValiditySeconds)
                .authorizedGrantTypes(authorizedGrantTypes)
                .redirectUris(RESOURCE_CLIENT_URL)
                .authorities("admin","ROLE_ADMIN")
                .scopes("all", "read", "write");
    }
    @Autowired
    @Qualifier(value = "jwtTokenStore")
    private TokenStore jwtTokenStore;

    @Autowired
    @Qualifier(value = "jwtTokenEnhancer")
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    @Qualifier(value = "jwtAccessTokenConverter")
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        log.info("AuthorizationServerEndpointsConfigurer configure");
        super.configure(endpoints);
        /***
         * token模式
         */
        /*endpoints.tokenStore(defaultTokenStoreConfig())
                .authenticationManager(authenticationManager())
                .userDetailsService(userDetailsService())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenStore(defaultTokenStoreConfig())
                .addInterceptor(customHandlerInterceptor());*/

        /**
         * jwt模式
         */
        endpoints.tokenStore(jwtTokenStore)
                .authenticationManager(authenticationManager())
                .userDetailsService(userDetailsService())
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST,HttpMethod.PUT,HttpMethod.DELETE,HttpMethod.OPTIONS)
                .addInterceptor(customHandlerInterceptor());
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add(jwtTokenEnhancer);
        enhancerList.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancerList);
        endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
    }

    /**
     * 默认注释
     */
    /*@Bean
    public TokenStore defaultTokenStoreConfig(){
        return new DefaultTokenStoreConfig();
    }*/


    @Bean
    public HandlerInterceptor customHandlerInterceptor(){
        return new CustomHandlerInterceptor();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetailsServiceImpl();
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        AuthenticationManager authenticationManager = new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return authenticationProvider().authenticate(authentication);
            }
        };
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsChecker userDetailsChecker(){
        return new CustomUserDetailsChecker();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setPostAuthenticationChecks(userDetailsChecker());
        return daoAuthenticationProvider;
    }
}
