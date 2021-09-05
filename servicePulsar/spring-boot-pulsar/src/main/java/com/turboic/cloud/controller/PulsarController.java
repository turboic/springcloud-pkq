
package com.turboic.cloud.controller;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.turboic.cloud.config.Msg;
import com.turboic.cloud.producer.PulsarProducerService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author liebe
 */

@Api(tags = {"Pulsar API"}, value = "spring-boot-pulsar")
@RestController
@RequestMapping(value = "/pulsar")
public class PulsarController {
    private static final Logger logger = LoggerFactory.getLogger(PulsarProducerService.class);

    @Autowired
    private PulsarProducerService pulsarProducerService;

    @PutMapping("/put}")
    @ApiOperation("PUT接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "header", value = "消息头", required = true, dataType = "String"),
            @ApiImplicitParam(name = "body", value = "消息体", required = true, dataType = "String")
    })
    public String get(@RequestParam(value = "header") String header,
                      @RequestParam(value = "body") String body) {

        logger.info("call put header:{}, body:{} ", header, body);
        Snowflake snowflake = IdUtil.getSnowflake(6, 6);
        Msg msg = new Msg(snowflake.nextId(), header, body);
        pulsarProducerService.send(msg);
        return JSONUtil.parseObj(msg).toString();
    }

    @PostMapping("/post")
    @ApiOperation("POST接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "msg", value = "Msg类型JSON参数", required = true, dataType = "Msg")
    })
    public String postMap(@RequestBody Msg msg) {
        logger.info("call post:  " + msg);

        pulsarProducerService.send(msg);
        return JSONUtil.parseObj(msg).toString();
    }

    @PostMapping("/v2/list")
    @ApiOperation("POST接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "list", value = "list参数", required = true, dataType = "List")
    })
    public String postList(@RequestBody List<Msg> msgList) {
        return "call Post List from :  " + msgList;
    }

    @PutMapping("/v1/put")
    @ApiOperation("PUT接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Msg", value = "消息内容Msg", required = true, dataType = "Msg")
    })
    public String put(@RequestBody Msg msg) {
        return "call put Msg By " + msg;
    }


    @DeleteMapping("/v1/delete/{id}")
    @ApiOperation("DELETE接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "MsgID", required = true, dataType = "String")
    })
    public String delete(@PathVariable String id) {
        return "call delete Msg By " + id;
    }
}
