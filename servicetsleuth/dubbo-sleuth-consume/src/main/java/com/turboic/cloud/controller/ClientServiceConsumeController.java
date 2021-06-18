package com.turboic.cloud.controller;
import com.turboic.cloud.api.ConsumeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试dubbo的rpc调用方式
 * @author liebe
 * 访问地址
 * http://localhost:9602/consume/execute/liebe
 */
@RestController
@RequestMapping("/consume")
public class ClientServiceConsumeController {
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceConsumeController.class);
    private final ConsumeService consumeService;

    public ClientServiceConsumeController(ConsumeService consumeService) {
        this.consumeService = consumeService;
    }

    @RequestMapping("/execute/{consume}")
    public String clientServiceProvider(@PathVariable(value="consume") String consume){
        logger.info("1、执行dubbo consume的Controller。");

        String api = consumeService.consume(consume);

        logger.info("5、执行dubbo provider 返回的结果:{}",api);

        if(StringUtils.isEmpty(api)){
            api = "莫醒醒";
        }

        return api;
    }

    @RequestMapping("/selectById/{id}")
    public String selectById(@PathVariable(value="id") String id){
        return consumeService.selectById(id);
    }
}
