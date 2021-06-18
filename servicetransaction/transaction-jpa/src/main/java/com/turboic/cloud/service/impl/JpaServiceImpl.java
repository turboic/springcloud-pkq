package com.turboic.cloud.service.impl;
import com.turboic.cloud.domain.UserInfoModel;
import com.turboic.cloud.repository.UserInfoRepository;
import com.turboic.cloud.service.JpaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author liebe
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class JpaServiceImpl implements JpaService {
    private static final Logger logger = LoggerFactory.getLogger(JpaServiceImpl.class);
    private static final String delete = "delete";


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private RestTemplate restTemplate;


    @Override
    public UserInfoModel save(UserInfoModel userInfo) {
        return userInfoRepository.save(userInfo);
    }

    /**
     * 抛出异常，事务有效
     * @param userInfo
     * @return
     */
    @Override
    public String transaction(UserInfoModel userInfo) {
        List<UserInfoModel> userInfoList = userInfoRepository.findAll();
        userInfoList.forEach(u->{
            logger.error("打印信息:{}",u.toString());
            if(userInfo.getName().equalsIgnoreCase(delete)){
                userInfoRepository.delete(u);
            }
        });
        userInfoRepository.save(userInfo);
        if(userInfo == null || userInfo.getName().equalsIgnoreCase("liebe")){
            throw new RuntimeException("主动抛出异常");
        }
        try{
            /**
             * 主动抛出异常事务注解生效
             */
            String result = restTemplate.getForObject("http://localhost:8080/web",String.class);
            logger.error("打印信息result:{}",result);
        }catch (Exception e){
            throw e;
        }
        Optional<UserInfoModel> userInfoOptional = userInfoRepository.findById(userInfo.getId());
        if(userInfoOptional.isPresent()){
            UserInfoModel ui = userInfoOptional.get();
            ui.setName("汉唐小馆的肉夹馍真贵啊");
            userInfoRepository.save(ui);
        }
        return "事务操作成功";
    }

    @Override
    public String update(UserInfoModel update) {
        logger.error("新建前:{}",update.toString());
        userInfoRepository.save(update);
        String id = update.getId();
        logger.error("数据ID:{}",id);

        List<UserInfoModel> us = userInfoRepository.findAll();
        us.stream().forEach(userInfo -> {
            userInfo.setName("北京");
            userInfo.setDate(new Date(1921929192L));
            userInfoRepository.save(userInfo);
            logger.error("新建前:{}",userInfo.toString());
        });

        return "更新过程完成";
    }
}
