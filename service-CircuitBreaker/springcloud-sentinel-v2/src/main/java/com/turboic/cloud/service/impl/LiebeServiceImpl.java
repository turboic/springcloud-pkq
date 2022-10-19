package com.turboic.cloud.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.turboic.cloud.entity.Liebe;
import com.turboic.cloud.mapper.LiebeMapper;
import com.turboic.cloud.service.LiebeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.UUID;

/**
 * @author liebe
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LiebeServiceImpl extends ServiceImpl<LiebeMapper, Liebe> implements LiebeService {
    private static final Logger log = LoggerFactory.getLogger(LiebeServiceImpl.class);
    @Override
    public void processing(Liebe liebe) {
        createDefaultLiebe();
        log.debug("1,创建默认值完成");
        if(StringUtils.isEmpty(liebe.getId())){
            liebe.setId(UUID.randomUUID().toString());
        }
        boolean result = this.save(liebe);
        log.debug("2,保存业务值完成");
        if(result){
            log.debug("3,进入业务逻辑，触发异常发生数据回滚");
            //throw new NullPointerException();
        }
        Liebe entity = this.getById(liebe.getId());
        entity.setPassword("mybatis事务性测试");
        entity.setUsername("我爱学习");
        this.saveOrUpdate(entity);
        log.debug("4,更新数据完成");
        log.info("service方法执行成功");

    }


    private void createDefaultLiebe(){
        String defaultLiebeId = System.currentTimeMillis()+"";
        Liebe liebe = new Liebe(defaultLiebeId,"root","root");
        this.save(liebe);
    }

}
