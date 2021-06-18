package com.turboic.cloud.service;
import com.turboic.cloud.pojo.SwaggerUserInfo;

/**
 * @author liebe
 */
public interface DubboReferenceService {
    SwaggerUserInfo getUser(String username);
}
