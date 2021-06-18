package com.turboic.cloud.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author taiji
 */
public class CustomUserDetailsChecker implements UserDetailsChecker {
    private final static String DEFAULT_USER_NAME = "admin";
    @Override
    public void check(UserDetails userDetails) {
        if(userDetails == null){
            throw new IllegalStateException("认证凭证错误");
        }
        if(!userDetails.getUsername().equalsIgnoreCase(DEFAULT_USER_NAME)){
            throw new IllegalArgumentException("认证凭证的用户名不正确");
        }
    }
}
