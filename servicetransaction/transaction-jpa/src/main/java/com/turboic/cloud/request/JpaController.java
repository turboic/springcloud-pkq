package com.turboic.cloud.request;
import com.turboic.cloud.domain.UserInfoModel;
import com.turboic.cloud.service.JpaService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * jpa-service的controller
 * @author liebe
 */
@Api(value="jpa transaction invoke的controller",tags={"jpa事务的接口"})
@RestController
@RequestMapping("/service-jpa")
@Slf4j
public class JpaController {
    private static final Logger logger = LoggerFactory.getLogger(JpaController.class);
    private final JpaService jpaService;

    public JpaController(JpaService jpaService) {
        this.jpaService = jpaService;
    }


    @RequestMapping("/save")
    public UserInfoModel save(@RequestParam(value="name") String name){
        UserInfoModel userInfo = new UserInfoModel();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setName(name);
        userInfo.setDate(new Date());
        return jpaService.save(userInfo);
    }

    /**
     * 抛出异常，事务有效，service事务
     * controller可以捕获打印日志了
     * @param name
     * @return
     */
    @RequestMapping("/transaction")
    public String transaction(@RequestParam(value="name") String name) {
        UserInfoModel userInfo = new UserInfoModel();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setName(name);
        userInfo.setDate(new Date());
        String result;
        try {
            result = jpaService.transaction(userInfo);
        } catch (Exception e) {
            logger.error("事务测试出现异常:{}", e);
            result = "事务测试出现异常:{}" + e;
        }
        return result;
    }

    @RequestMapping("/update")
    public String update(@RequestParam(value="name") String name) {
        String uuid = "78356289-912457694-2020-06";
        UserInfoModel update = new UserInfoModel();
        update.setId(uuid);
        update.setName(name);
        update.setDate(new Date());
        String result;
        try {
            result = jpaService.update(update);
        } catch (Exception e) {
            logger.error("更新出现异常:{}", e);
            result = "更新出现异常:{}" + e;
        }
        MyThread thread = new MyThread(update);
        thread.start();
        thread.setDaemon(true);
        return result;
    }

    class MyThread extends Thread{
        private UserInfoModel userInfo;
        public MyThread(UserInfoModel update){
            this.userInfo = update;
        }

        @Override
        public void run() {
            while (true) {
                String result;
                try {
                    result = jpaService.update(userInfo);
                } catch (Exception e) {
                    logger.error("更新出现异常:{}", e);
                    result = "更新出现异常:{}" + e;
                    throw e;
                }
                logger.error("线程执行结果:{}", result);
            }

        }
    }
}
