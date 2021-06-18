package com.turboic.cloud.controller;
import com.turboic.cloud.dubbo.ConsumerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/consumer")
public class ConsumerController {
    private final ConsumerService consumerService;

    public ConsumerController(ConsumerService consumerService){
        this.consumerService = consumerService;
    }

    @GetMapping("/get/{parameter}")
    public String getRequestMapping(@PathVariable(value = "parameter") String parameter){
        return consumerService.execute(parameter);
    }
}
