package com.turboic.cloud.service;

import com.turboic.cloud.entity.SwaggerUser;

/**
 * @author liebe 用户接口
 */

public interface SwaggerUserService {

    SwaggerUser getUser(String username);

}
