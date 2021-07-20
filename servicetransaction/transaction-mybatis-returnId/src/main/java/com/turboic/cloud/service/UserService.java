package com.turboic.cloud.service;
import com.turboic.cloud.entity.User;

/**
 * @author liebe
 */
public interface UserService {

    /**
     * 使用useGeneratedKeys
     * @param user
     * @return
     */
    int insertUseGeneratedKeyAsId(User user);


    /**
     * 使用SELECT LAST_INSERT_ID()
     * @param user
     * @return
     */
    int insertLastInsertId(User user);
}
