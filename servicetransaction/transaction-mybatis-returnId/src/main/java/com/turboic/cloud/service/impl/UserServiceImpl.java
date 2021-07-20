package com.turboic.cloud.service.impl;
import com.turboic.cloud.entity.User;
import com.turboic.cloud.mapper.UserMapper;
import com.turboic.cloud.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liebe
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public int insertUseGeneratedKeyAsId(User user) {
        return userMapper.insertUseGeneratedKeyAsId(user);
    }

    @Override
    public int insertLastInsertId(User user) {
        return userMapper.insertLastInsertId(user);
    }
}
