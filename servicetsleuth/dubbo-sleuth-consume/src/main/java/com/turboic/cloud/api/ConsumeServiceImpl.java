package com.turboic.cloud.api;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author liebe
 *
 * 先启动dubbo service provider的实例程序
 */
@Service
public class ConsumeServiceImpl implements ConsumeService{

    private static final Logger logger = LoggerFactory.getLogger(ConsumeServiceImpl.class);


    /***
     * dubbo的引用
     */
    /*@DubboReference(
            version = "1.0.0",
            url = "dubbo://192.168.124.8:6360",
            timeout = 100,
            methods = {
                    @Method(name = "api", timeout = 300)
            }
    )*/

    @DubboReference(
            version = "1.0.0",
            timeout = 300,
            loadbalance = "roundrobin",
            group = "group",
            check = false,//配置启动不必检查dubbo provider的健康状态，避免provider的影响启动不成功
            //async = true,//开启异步，设置为true，可能出现，调用接口返回null的情况
            interfaceClass = ApiService.class,
            methods = {
                    @Method(name = "api", timeout = 100)
            }
    )
    private ApiService apiService;


    @HystrixCommand(fallbackMethod = "fallbackMethodError")
    @Override
    public String consume(String provider) {
        logger.info("2、执行dubbo consume的嵌套Service方法。");
        logger.info("dubbo consume call provider service");
        logger.info("consume parameter:{}",provider);

        logger.info("3、执行dubbo consume service开始调用dubbo provider的方法。");

        String apiServiceApiProvider = apiService.api(provider);
        logger.info("return apiService.api(provider):{}",apiServiceApiProvider);
        return apiServiceApiProvider;
    }


    @HystrixCommand(fallbackMethod = "fallbackMethodError")
    @Override
    public String insert(String name) {
        logger.info("consume parameter:{}",name);
        String apiServiceApiProvider = apiService.insert(name);
        logger.info("return apiService.insert(name):{}",apiServiceApiProvider);
        return apiServiceApiProvider;
    }

    /**
     * 测试Mybatis的一级缓存
     * @param id
     * @return
     */
    @HystrixCommand(fallbackMethod = "fallbackMethodError")
    @Override
    public String selectById(String id) {
        logger.info("consume parameter:{}",id);
        String apiServiceApiProvider = apiService.selectById(id);
        logger.info("return apiService.selectById(id):{}",apiServiceApiProvider);
        return apiServiceApiProvider;
    }


    @Override
    public String fallbackMethodError(String provider){
        return "Hello "+provider+",dubbo所有的provider 实例服务已经停止了，@HystrixCommand熔断机制已经生效了";
    }
}
