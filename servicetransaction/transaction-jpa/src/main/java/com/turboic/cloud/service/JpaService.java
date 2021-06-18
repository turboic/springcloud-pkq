package com.turboic.cloud.service;

import com.turboic.cloud.domain.UserInfoModel;

/**
 * @author liebe
 */
public interface JpaService {
    /***
     *
     * @param userInfo
     * @return UserInfo
     */
    UserInfoModel save(UserInfoModel userInfo);

    String transaction(UserInfoModel userInfo);

    String update(UserInfoModel update);
}
