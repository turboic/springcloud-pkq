package com.turboic.cloud.service.impl;
import com.turboic.cloud.pojo.SwaggerUser;
import com.turboic.cloud.service.SwaggerUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liebe
 * dubbo服务的接口
 */
public class SwaggerUserServiceImpl implements SwaggerUserService {
    private static final Logger log = LoggerFactory.getLogger(SwaggerUserServiceImpl.class);

    @Override
    public SwaggerUser getUser(String username) {
        SwaggerUser swaggerUser = new SwaggerUser(username,"dubbo");
        swaggerUser.setUsername("当你看到这条消息时，已经完成了spring-boot2与dubbo2.7.7的功能性整合");
        swaggerUser.setPassword("垃圾分类");
        log.debug("dubbo服务提供者服务接口:{}",swaggerUser.toString());
        log.info("dubbo服务提供者接口SwaggerUserServiceImpl被调用了");
        return swaggerUser;
    }
}
