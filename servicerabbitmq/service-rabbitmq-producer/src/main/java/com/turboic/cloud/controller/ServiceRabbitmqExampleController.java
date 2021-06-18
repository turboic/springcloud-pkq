package com.turboic.cloud.controller;
import com.turboic.cloud.confirm.RabbitMqProducerConfirm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author liebe
 */
@RestController
@RequestMapping(value = "/rabbitmq-example")
public class ServiceRabbitmqExampleController
{
    @Autowired
    private RabbitMqProducerConfirm rabbitMqProducerConfirm;
    private static final Logger logger = LoggerFactory.getLogger(ServiceRabbitmqExampleController.class);

    @GetMapping(value = "/getUserInfo/{msg}")
    public String getUserInfo(@PathVariable(value = "msg") String msg){
        rabbitMqProducerConfirm.send(msg);
        return "1";
    }

}
