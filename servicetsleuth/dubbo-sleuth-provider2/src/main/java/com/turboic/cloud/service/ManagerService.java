package com.turboic.cloud.service;

import com.turboic.cloud.entity.Tencent;

/**
 * @author liebe
 */
public interface ManagerService {
    int insert(Tencent tencent);
    Tencent selectByPrimaryKey(Integer id);
    void executeTest(Integer id);
}
