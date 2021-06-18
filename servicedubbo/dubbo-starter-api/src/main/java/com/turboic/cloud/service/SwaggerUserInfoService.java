package com.turboic.cloud.service;

import com.turboic.cloud.pojo.SwaggerUserInfo;

/**
 * @author liebe 用户接口
 */

public interface SwaggerUserInfoService {

    SwaggerUserInfo getUser(String username);

}
