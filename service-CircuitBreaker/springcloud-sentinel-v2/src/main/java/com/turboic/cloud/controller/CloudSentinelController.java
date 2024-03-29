package com.turboic.cloud.controller;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSON;
import com.turboic.cloud.config.Swagger2Controller;
import com.turboic.cloud.service.CloudSentinelService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "sentinel的controller", tags = {"sentinel-demo演示接口"})
@RestController
@RequestMapping("/sentinel")
@Swagger2Controller(value = "使用sentinel-ui的Controller")
public class CloudSentinelController {
    public CloudSentinelController(CloudSentinelService cloudSentinelService) {
        this.cloudSentinelService = cloudSentinelService;
    }
    
    @GetMapping("/exec/{number}")
    @SentinelResource(value = "exec", blockHandler = "blockHandler", entryType = EntryType.OUT, fallback = "fallback")
    public String exec(@PathVariable(value = "number") Integer number) {
        System.out.println("exec method name:" + number);
        return "hello sentinel" + number;
    }

    public String fallback(Integer number) {
        System.out.println("exec method name:" + number);
        return "hello sentinel fallback " + number;
    }

    public String blockHandler(Integer number, BlockException blockException) {
        System.out.println("限流与阻塞处理,执行方法blockHandler:参数number:" + number + "，异常原因是:" + blockException.getMessage());
        return "限流与阻塞处理,执行方法blockHandler:参数number:" + number + "，异常原因是:" + JSON.toJSONString(blockException);
    }

    /***
     * 招不到注入，需要开启功能开关
     * @EnableFeignClients
     */
    private final CloudSentinelService cloudSentinelService;

    @GetMapping("/hello/{name}")
    @SentinelResource(value = "hello", blockHandler = "blockHandler", entryType = EntryType.OUT)
    public String hello(@PathVariable(value = "name") String name) {
        System.out.println("hello method name:" + name);
        return cloudSentinelService.cloud(name);
    }
}
