package com.turboic.cloud.config;

import de.codecentric.boot.admin.server.cloud.discovery.DefaultServiceInstanceConverter;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author liebe
 */
@Component
public class NacosServiceInstanceConverter extends DefaultServiceInstanceConverter {

    private static final String KEY_SERVLET_CONTEXT_PATH = "server.servlet.context-path";

    @Override
    protected URI getServiceUrl(ServiceInstance instance) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(instance.getUri());

        String contextPath = instance.getMetadata().get(KEY_SERVLET_CONTEXT_PATH);
        if (!StringUtils.isEmpty(contextPath)) {
            uriComponentsBuilder.path(contextPath);
        }

        return uriComponentsBuilder.path("/").build().toUri();
    }
}