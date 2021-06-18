package com.turboic.cloud.request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liebe
 */
@RestController
@RequestMapping("/gateway")
@Slf4j
public class ConsumeProviderController {
    @RequestMapping("/consume")
    public String consume(String name){
        log.info("gateway-consume微服务模块的Controller");
        return "晚上好，该睡觉了，哈哈哈哈哈哈 "+name;
    }


    @RequestMapping("/route")
    public String route(){
        log.info("通过gateway路由测试转发到此处的服务接口了");
        /***此处模拟超时时间，通过gateway网关调用，触发服务降级的超时***/
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            log.error("服务Interrupt异常:{}",e);
        }
        return "hi,gateway,遇见你是我的缘";
    }
}
