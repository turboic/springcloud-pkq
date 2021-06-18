package com.turboic.cloud.request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认降级处理
 * @author liebe
 */
@RestController
@Slf4j
public class DefaultHystrixController {

    /**
     * 定义降级处理方法
     * @return
     */
    @RequestMapping("/fallback")
    public Map<String,String> fallback(){
        log.info("请求微服务出现故障，默认执行熔断降级操作");
        Map<String,String> map = new HashMap<>(3);
        map.put("returnCode","500");
        map.put("returnMsg","服务器迷糊中，稍后尝试操作吧呵呵呵");
        map.put("returnObj","越减越肥，到底怎么回事");
        map.put("returnTip","当你看到这个提示消息，说明SpringCloudGateway集成服务熔断降级的功能生效了，哈哈哈哈");
        map.put("returnDate","今天时2020年5月30日 13:39 北京，星期六");
        map.put("returnDuoYu","SpringCloudGateway网关修改重启，相关微服务也要重启操作哦");
        return map;
    }
}
