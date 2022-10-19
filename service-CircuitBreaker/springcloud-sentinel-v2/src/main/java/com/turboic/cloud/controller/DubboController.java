package com.turboic.cloud.controller;
import com.turboic.cloud.config.Swagger2Controller;
import com.turboic.cloud.dubbo.DubboConsumeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(value = "dubbo的controller", tags = {"dubbo-demo演示接口"})
@RestController
@RequestMapping("/dubbo")
@Swagger2Controller(value = "使用dubbo的Controller")
public class DubboController {
    private final DubboConsumeService dubboConsumeService;
    public DubboController(DubboConsumeService dubboConsumeService) {
        this.dubboConsumeService = dubboConsumeService;
    }

    @GetMapping("/test/{dubbo}")
    public String test(@PathVariable(value = "dubbo") String dubbo) {
        System.out.println("test method dubbo:"+dubbo);
        return dubboConsumeService.test(dubbo);
    }
}
