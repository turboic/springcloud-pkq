package com.turboic.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * 测试oauth-server的controller
 * @author liebe
 */
@Api(value="oauth-server的controller",tags={"oauth-server演示接口"})
@RestController
@RequestMapping("/server")
public class Oauth2ServerController {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2ServerController.class);

    public Oauth2ServerController(){
    }


    @GetMapping("/oauth2")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Object get(Principal principal){
        logger.info("请求地址:/server/oauth2");
        return "请求地址:/server/oauth2成功"+principal.toString();
    }
}
