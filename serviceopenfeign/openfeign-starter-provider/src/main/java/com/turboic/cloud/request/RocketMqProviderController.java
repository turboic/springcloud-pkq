package com.turboic.cloud.request;
import com.turboic.cloud.ScanSwaggerObj;
import com.turboic.cloud.starter.RocketConst;
import com.turboic.cloud.starter.RocketMqStarterProvider;
import com.turboic.cloud.transaction.RocketMqStarterTransactionProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试rocket send message swagger-ui的controller
 * @author liebe
 */
@Api(value="swagger的controller",tags={"swagger-RcoketMq发送消息演示接口"})
@RestController
@RequestMapping("/rocketmq")
@ScanSwaggerObj(value = "使用Swagger-ui的Controller")
public class RocketMqProviderController {
    private static final Logger logger = LoggerFactory.getLogger(RocketMqProviderController.class);
    @Autowired
    private RocketMqStarterTransactionProvider rocketMqStarterTransactionProvider;
    @Autowired
    private RocketMqStarterProvider rocketMqStarterProvider;
    @ApiOperation(value="用户接口", notes="rocketmq消息发送接口", produces="application/json",httpMethod="POST")
    @ApiImplicitParams({ @ApiImplicitParam(name="msg",value="消息内容",paramType = "query", dataType = "String", required = true,example = "hello world")})
    @RequestMapping(value="/provider",method= RequestMethod.POST)
    public String provider(@RequestParam(value = "msg") String msg) {
        logger.info("消息内容：{}", msg);
        return rocketMqStarterProvider.send(RocketConst.destination,msg);
    }

    @ApiOperation(value="用户接口", notes="rocketmq消息事务发送接口", produces="application/json",httpMethod="POST")
    @ApiImplicitParams({ @ApiImplicitParam(name="msg",value="消息事务内容",paramType = "query", dataType = "String", required = true,example = "hello world")})
    @RequestMapping(value="/transaction",method= RequestMethod.POST)
    public String transaction(@RequestParam(value = "msg") String msg) {
        logger.info("事务消息内容：{}", msg);
        TransactionSendResult transactionSendResult = rocketMqStarterTransactionProvider.transaction(RocketConst.destination_transaction,msg);
        return "rocketmq消息事务发送完成 ";
    }
}
