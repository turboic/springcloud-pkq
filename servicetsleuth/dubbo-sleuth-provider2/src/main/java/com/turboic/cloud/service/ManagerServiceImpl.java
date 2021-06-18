package com.turboic.cloud.service;

import com.turboic.cloud.entity.Tencent;
import com.turboic.cloud.mapper.TencentMapper;
import com.turboic.cloud.utils.FastJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.*;

/***
 * 自身使用，非dubbo外暴露的service接口
 * @author liebe
 */

@Service
@Transactional(
        value = "transactionManager",
        isolation = Isolation.DEFAULT,
        propagation = Propagation.REQUIRED,
        rollbackFor = Exception.class,
        timeout=36000)
public class ManagerServiceImpl implements ManagerService {
    private static final Logger logger = LoggerFactory.getLogger(ManagerServiceImpl.class);

    private final static Integer DEFAULT_SELECT_COUNT = 100;

    private final TencentMapper tencentMapper;

    public ManagerServiceImpl(TencentMapper tencentMapper) {
        this.tencentMapper = tencentMapper;
    }


    @Override
    public int insert(Tencent tencent) {
        return tencentMapper.insert(tencent);
    }
    @Override
    public Tencent selectByPrimaryKey(Integer id) {
        return tencentMapper.selectByPrimaryKey(id);
    }
    @Override
    public void executeTest(Integer id){
        LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<Runnable>();
        ExecutorService  executorService = new ThreadPoolExecutor(
                10,
                20,
                5,
                TimeUnit.SECONDS,
                linkedBlockingQueue,
                new ThreadPoolExecutor.AbortPolicy()
        );
        executorService.execute(() -> {
                    for (int j = 0; j < DEFAULT_SELECT_COUNT; j++) {
                        logger.info(FastJsonUtils.javaBeanToJson(selectByPrimaryKey(id)));
                    }
                }
        );
        executorService.shutdown();
    }
}
