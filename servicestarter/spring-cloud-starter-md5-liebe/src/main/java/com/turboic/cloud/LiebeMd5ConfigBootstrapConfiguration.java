package com.turboic.cloud;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liebe
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "spring.cloud.liebe.md5.config.enabled", matchIfMissing = true)
public class LiebeMd5ConfigBootstrapConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LiebeMd5ConfigProperties liebeMd5ConfigProperties() {
        return new LiebeMd5ConfigProperties();
    }

}
