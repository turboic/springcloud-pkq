package com.turboic.cloud.service.impl;
import com.turboic.cloud.pojo.SwaggerUser;
import com.turboic.cloud.service.SwaggerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author liebe
 * dubbo服务的接口
 */
@DubboService(version="1.0.0",group="liebe",interfaceName="swaggerUserService")
@Slf4j
public class SwaggerUserServiceImpl implements SwaggerUserService {

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
