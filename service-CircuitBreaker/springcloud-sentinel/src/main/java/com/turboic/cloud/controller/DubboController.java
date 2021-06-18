package com.turboic.cloud.controller;
import com.turboic.cloud.dubbo.DubboConsumeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
