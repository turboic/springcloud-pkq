package com.turboic.cloud.controller;
import com.turboic.cloud.mq.RocketmqProducerConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/producer")
public class ProducerController {

    private final RocketmqProducerConfig rocketmqProducerConfig;

    public ProducerController(RocketmqProducerConfig rocketmqProducerConfig) {
        this.rocketmqProducerConfig = rocketmqProducerConfig;
    }

    @GetMapping(value = "/send/{msg}")
    public String sendMsg(@PathVariable(value = "msg",required = true) String msg){
        return rocketmqProducerConfig.send(msg);
    }
    @GetMapping(value = "/sendOneWay/{msg}")
    public String sendOneWay(@PathVariable(value = "msg",required = true) String msg){
        return rocketmqProducerConfig.sendOneWay(msg);
    }

    @GetMapping(value = "/sendMessageInTransaction/{msg}")
    public String sendMessageInTransaction(@PathVariable(value = "msg",required = true) String msg){
        return rocketmqProducerConfig.sendMessageInTransaction(msg);
    }
}
