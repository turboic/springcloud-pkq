package com.turboic.cloud.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liebe
 */
@Configuration
public class JwtTokenConfig{
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenConfig.class);

    @Bean(value = "jwtTokenStore")
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }
    @Bean(value = "jwtTokenEnhancer")
    public TokenEnhancer jwtTokenEnhancer(){
        return new JwtTokenEnhancer();
    }


    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE_PASSWORD = "password";
    @Bean(value = "jwtAccessTokenConverter")
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter(){
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                String grantType = authentication.getOAuth2Request().getGrantType();
                if(AUTHORIZATION_CODE.equals(grantType) || GRANT_TYPE_PASSWORD.equals(grantType)) {
                    logger.info("授权方式类型:{}",grantType);
                }
                String userName = authentication.getUserAuthentication().getName();
                Map<String, Object> additionalInformation = new HashMap<String, Object>(16);
                additionalInformation.put("account", userName);
                additionalInformation.put("info",  JwtUtil.generateToken());
                additionalInformation = Collections.unmodifiableMap(additionalInformation);
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                OAuth2AccessToken token = super.enhance(accessToken, authentication);
                return token;
            }
        };
        converter.setSigningKey("liebe");
        return converter;
    }
}