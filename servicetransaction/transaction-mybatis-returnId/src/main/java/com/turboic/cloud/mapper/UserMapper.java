package com.turboic.cloud.mapper;
import com.turboic.cloud.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 定义MyBatis的接口类
 * @author liebe
 */
@Repository
@Mapper
public interface UserMapper {

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