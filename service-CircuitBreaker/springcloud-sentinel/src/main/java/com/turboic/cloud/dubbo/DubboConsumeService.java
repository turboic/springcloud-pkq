package com.turboic.cloud.dubbo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Component;
import java.util.logging.Logger;

@Component
public class DubboConsumeService implements CommonService {

    private static final Logger log = Logger.getLogger(DubboConsumeService.class.getName());

    /***
     * 这里设置成异步async=true，调用成功收不到返回的结果呢
     */
    @Reference(interfaceClass = CommonService.class, interfaceName = "commonService", retries = 5, async = false)
    CommonService commonService;

    @Override
    public String test(String dubbo) {
        log.info("dubbo服务消费者接收调用请求={}" + dubbo);
        return commonService.test(dubbo);
    }
}