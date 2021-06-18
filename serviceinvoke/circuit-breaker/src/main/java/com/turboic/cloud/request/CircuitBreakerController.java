package com.turboic.cloud.request;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.turboic.cloud.feign.DemoFeignService;
import com.turboic.cloud.md5.LiebeMd5Util;
import com.turboic.cloud.service.CircuitBreakerService;
import com.turboic.cloud.vo.App;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
/**
 * @author liebe
 *
 * 服务的消费者，根据服务名称进行调用
 *
 *
 */

@RestController
@Slf4j
public class CircuitBreakerController {

    private final CircuitBreakerService circuitBreakerService;
    private final RestTemplate restTemplate;

    public CircuitBreakerController(CircuitBreakerService circuitBreakerService,RestTemplate restTemplate) {
        this.circuitBreakerService = circuitBreakerService;
        this.restTemplate = restTemplate;
    }

    @Autowired
    private DemoFeignService demoFeignService;

    /**
     * 测试ribbon的熔断机制
     */
    @HystrixCommand(fallbackMethod = "ribbonFallBack")
    @GetMapping("/ribbonInvoke")
    public String ribbonInvoke(String name) {
        String url = "http://provider-service/provider-service/ribbon?name=" + name;
        Object result = restTemplate.getForObject(url, String.class);
        log.info("ribbon调用微服务:{},返回结果:{}","provider-service",result.toString());
        return result.toString();
    }

    public String ribbonFallBack(String name) {
        log.error("熔断，默认回调函数");
        return "{\"id\":\"ribbon-restTemplate微服务调用\",\"name\":\"ribbon\",\"password\":\"服务降级\",\"description\":\"使用ribbon的restTemplate调用微服务出现异常，您看到的是降级效果\"}";
    }


    /**
     * 测试feign熔断
     * @param feign
     * @return
     */
    @RequestMapping("/feignInvoke")
    public String feignInvoke(String feign) {
        log.info("open-feign调用微服务参数接口参数:{}",feign);
        String result = demoFeignService.feignInvoke(feign);
        log.info("feign调用微服务:{},返回结果:{}","provider-service",result);
        return result.toString();
    }



    @GetMapping(value = "circuitGet/{id}")
    @HystrixCommand(fallbackMethod = "processHystrix_circuitGet")
    public App circuitGet(@PathVariable("id") String id, Exception exception) {
        return circuitBreakerService.circuitGet(id);
    }

    public App processHystrix_circuitGet(@PathVariable("id") String id,Exception e) {
        log.info("circuitGet方法出现异常，触发processHystrix_circuitGet的熔断降级处理方法:{}",id);
        App app = new App();
        app.setDate(new Date());
        app.setDescription("description");
        app.setName("抖音");
        app.setVersion(id);
        log.info("因为调用超时触发了服务的降级",id);
        return app;
    }

    @Autowired
    private LiebeMd5Util liebeMd5Util;
    @RequestMapping(value = "/mappingStarter")
    public String mappingStarter(String name){
        log.info("后台接收参数:{}",name);
        String content= liebeMd5Util.computer(name);
        log.info("使用starter编码数据:{}",content);
        return content;
    }
}
