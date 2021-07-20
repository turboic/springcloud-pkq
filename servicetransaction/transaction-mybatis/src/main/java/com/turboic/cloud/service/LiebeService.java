package com.turboic.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.turboic.cloud.entity.Liebe;

/**
 * @author liebe
 */
public interface LiebeService extends IService<Liebe> {

    void processing(Liebe liebe);
    void saveData(Liebe liebe);
}
