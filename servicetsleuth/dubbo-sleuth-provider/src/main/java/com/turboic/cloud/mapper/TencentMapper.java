package com.turboic.cloud.mapper;

import com.turboic.cloud.entity.Tencent;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * @author liebe
 */

/***
 * 标志二级缓存
 * Caches collection already contains value
 * @CacheNamespace
 */
public interface TencentMapper {
    int deleteByPrimaryKey(Integer id);

    /*Tencent insertResult(Tencent record);*/

    /**
     * 插入语句，在主键自增的情况下,useGeneratedKeys = true,keyProperty = "xxx",将值赋给哪个属性，这个属性是方法参数中的
     * @param record
     * @return
     */
    int insert(Tencent record);

    int insertSelective(Tencent record);

    Tencent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Tencent record);

    int updateByPrimaryKey(Tencent record);
}