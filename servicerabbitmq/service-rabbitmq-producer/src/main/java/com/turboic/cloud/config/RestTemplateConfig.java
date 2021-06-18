package com.turboic.cloud.config;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * @author liebe
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        restTemplateBuilder.setConnectTimeout(Duration.ofHours(1));
        restTemplateBuilder.setReadTimeout(Duration.ofHours(1));
        restTemplateBuilder.setBufferRequestBody(true);
        restTemplateBuilder.detectRequestFactory(true);
        restTemplateBuilder.errorHandler(errorHandler());
        return restTemplateBuilder.build();
    }

    @Bean
    public ResponseErrorHandler errorHandler() {
        MyDefaultResponseErrorHandler defaultResponseErrorHandler = new MyDefaultResponseErrorHandler();
        return defaultResponseErrorHandler;
    }
}