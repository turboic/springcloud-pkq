package com.turboic.cloud.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turboic.cloud.entity.Liebe;
import com.turboic.cloud.mapper.LiebeMapper;
import com.turboic.cloud.service.LiebeService;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @author liebe
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class LiebeServiceImpl extends ServiceImpl<LiebeMapper, Liebe> implements LiebeService {
    @Override
    public void processing(Liebe liebe) {
        createDefaultLiebe();
        log.debug("1,创建默认值完成");
        if(StringUtils.isEmpty(liebe.getId())){
            liebe.setId(UUID.randomUUID().toString());
        }
        liebe.setCreatetime(new Timestamp(new Date().getTime()));
        liebe.setUpdatetime(new Timestamp(new Date().getTime()));
        liebe.setUsername("Hello transaction for mybatis 服务启动中");
        liebe.setPassword(UUID.randomUUID().toString());
        boolean result = this.save(liebe);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,
                10,2000L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(100),
                Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());


        executor.execute(() -> {
            List<Liebe> liebeList = new ArrayList<>(10000);
            for (int i = 0;i<10000;i++){
                Liebe l = new Liebe();
                l.setId(UUID.randomUUID().toString());
                l.setCreatetime(new Timestamp(new Date().getTime()));
                l.setUpdatetime(new Timestamp(new Date().getTime()));
                l.setUsername("批量");
                l.setPassword(UUID.randomUUID().toString());
                liebeList.add(l);
            }
            saveBatch(liebeList);
        });

        log.debug("2,保存业务值完成");
        if(result){
            log.debug("3,进入业务逻辑，触发异常发生数据回滚");
            //throw new NullPointerException();
        }
//        Liebe entity = this.getById(liebe.getId());
//        entity.setPassword(UUID.randomUUID().toString());
//        entity.setUsername("我爱学习");
//        this.saveOrUpdate(entity);
//        log.debug("4,更新数据完成");
//        log.info("service方法执行成功");

    }


    private void createDefaultLiebe(){
        String defaultLiebeId = System.currentTimeMillis()+"";
        Liebe liebe = new Liebe(defaultLiebeId,"root","root");
        liebe.setCreatetime(new Timestamp(new Date().getTime()));
        liebe.setUpdatetime(new Timestamp(new Date().getTime()));
        this.save(liebe);
    }

}
