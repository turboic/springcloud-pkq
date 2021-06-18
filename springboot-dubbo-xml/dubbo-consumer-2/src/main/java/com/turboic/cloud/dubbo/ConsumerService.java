package com.turboic.cloud.dubbo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;
@Service
public class ConsumerService {
    //这是使用@Reference注解获取不到接口实例apiService，并且dubbo-admin监控也无法检测到消费者的实例注入信息
    @DubboReference
    HelloService apiService;
    public String execute(String parameter) {
        return apiService.execute(parameter);
    }
}
