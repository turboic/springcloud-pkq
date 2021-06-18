package com.turboic.cloud.dubbo;

import org.apache.dubbo.config.annotation.Service;

import java.util.logging.Logger;

/**
 * @author liebe
 */
@Service(interfaceClass = CommonService.class,interfaceName="commonService", export = true,dynamic=true,register=true,
        weight=1,delay=0)
public class DubProviderServiceImpl implements CommonService{
    private static final Logger log = Logger.getLogger(DubProviderServiceImpl.class.getName());
    @Override
    public String test(String dub) {
        log.info("dumbo服务提供者接收调用请求={}"+dub);
        return "hello " +dub;
    }
}
