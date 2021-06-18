package com.turboic.cloud.api;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.turboic.cloud.entity.Tencent;
import com.turboic.cloud.mapper.TencentMapper;
import com.turboic.cloud.utils.FastJsonUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.ibatis.annotations.CacheNamespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;

/**
 * 使用DubboService注解，dubbo的版本是在2.7.7+
 * @author libe
 */
@DubboService(
        version="1.0.0",
        timeout=300 ,
        weight = 2,
        retries=3 ,
        loadbalance="roundrobin",
        group="group",
        interfaceClass = ApiService.class,
        methods = {
                @Method(name = "api", timeout = 100),
                @Method(name = "insert", timeout = 100)
        }
)
public class ProviderServiceImpl implements ApiService {
    private static final Logger logger = LoggerFactory.getLogger(ProviderServiceImpl.class);

    @Autowired
    private TencentMapper tencentMapper;

    /**
     * @param provider
     * @return
     */
    @HystrixCommand(
            groupKey = "groupKey",
            commandKey = "commandKey",
            threadPoolKey = "threadPoolKey",
            fallbackMethod = "apiError",
            /**
             * 更多commandProperies的配置，
             * 参考
             * https://github.com/Netflix/Hystrix/wiki/Configuration
            */
            commandProperties = {
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                //执行隔离策略
                /**
                 * The default, and the recommended setting, is to run HystrixCommands using thread isolation (THREAD)
                 * and HystrixObservableCommands using semaphore isolation (SEMAPHORE).
                 */
                @HystrixProperty(name = "execution.isolation.strategy",
                        value = "THREAD")
            },
            //指定不熔断处理的异常
            ignoreExceptions= IOException.class,
            //指定异常出现观察模式，默认
            observableExecutionMode = ObservableExecutionMode.EAGER,
            //指定默认的回调方法
            //defaultFallback = "fallbackProviderMethod",
            //如果熔断本身出现了异常
            raiseHystrixExceptions = HystrixException.RUNTIME_EXCEPTION
            )
    @Override
    public String api(String provider) throws RuntimeException {
        logger.info("4、真正执行dubbo provider service的方法,接收到的参数provider:{}。",provider);

        String result  = "故宫,"+provider+",“雨雪天”接连来袭 中国新一轮大范围雨雪降温已启程!";
        logger.info("api接口返回结果:"+result);
        return result;
    }

    /**
     * com.netflix.hystrix.contrib.javanica.exception.FallbackDefinitionException:
     * fallback method wasn't found: fallbackMethodError([class java.lang.String])
     * @return
     */
    @Override
    public String apiError(String provider){
        return provider+" dubbo provider出现错误， 服务调用失败,这里触发了provider的熔断机制！";
    }

    /*@HystrixCommand(
            fallbackMethod = "apiError"
    )*/
    @Override
    public String insert(String name) throws RuntimeException {
        logger.info("parameter:{}",name);
        Tencent tencent = new Tencent();
        tencent.setAddress("123");
        tencent.setMobile("1830000****");
        tencent.setName(name);
        int id = tencentMapper.insert(tencent);
        if(id > 0){
            logger.info("数据库插入成功");
        }
        return FastJsonUtils.javaBeanToJson(tencent);
    }


    @HystrixCommand(
           fallbackMethod = "apiError"
   )
    @Override

    public String selectById(String id)  {
        logger.info("parameter:{}",id);
        Integer idInt = Integer.parseInt(id);
        Tencent tencent = tencentMapper.selectByPrimaryKey(idInt);
        logger.info("第一次查询:{}",FastJsonUtils.javaBeanToJson(tencent));
        Tencent tencent2 = tencentMapper.selectByPrimaryKey(idInt);
        logger.info("第二次查询:{}",FastJsonUtils.javaBeanToJson(tencent2));
        logger.info("查询结果比较:{}",tencent.equals(tencent2));
        return FastJsonUtils.javaBeanToJson(tencent);
    }
}
