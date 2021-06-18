package com.turboic.cloud.service.impl;
import com.turboic.cloud.pojo.SwaggerUserInfo;
import com.turboic.cloud.service.DubboReferenceService;
import com.turboic.cloud.service.SwaggerUserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author liebe
 */
@Service
public class DubboReferenceServiceImpl implements DubboReferenceService {

    @DubboReference(version="1.0.0",group="liebe",interfaceName="swaggerUserService")
    private SwaggerUserInfoService swaggerUserInfoService;

    @Override
    public SwaggerUserInfo getUser(String username) {
        return swaggerUserInfoService.getUser(username);
    }
}
