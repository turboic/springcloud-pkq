package com.turboic.cloud.request;
import com.turboic.cloud.feign.IFeignDeliveryServiceClient;
import com.turboic.cloud.pojo.TradeOrder;
import com.turboic.cloud.pojo.WoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author liebe
 */
@RestController
@RequestMapping("/platform")
@Slf4j
public class SkywalkingMeiTuanController {

    @Autowired
    private IFeignDeliveryServiceClient iFeignDeliveryServiceClient;

    /***
     * 生成订单信息
     * 参考美团订单
     * 注意：这里需要加上注解RequestBody，否则获取不到请求的参数信息
     * pojo对象bean
     * @param woUser
     * @return
     */
    @PostMapping("/trade")
    public TradeOrder trade(@RequestBody WoUser woUser){
        log.info("交易platform收到用户外卖请求,根据请求信息:{}生成订单啦啦啦",woUser.toString());
        TradeOrder tradeOrder = new TradeOrder();
        tradeOrder.setCreatTime(new Date());
        tradeOrder.setId(UUID.randomUUID().toString());
        tradeOrder.setOrderNumber("8585 5542 3800 3179 2");
        tradeOrder.setPayType("在线支付");
        tradeOrder.setName(woUser.getName());
        tradeOrder.setMobile(woUser.getMobile());
        tradeOrder.setAddress(woUser.getAddress());
        tradeOrder.setUserCount(222);
        log.info("交易platform生成订单信息:{}",tradeOrder.toString());
        String delivery = iFeignDeliveryServiceClient.delivery(tradeOrder.getOrderNumber());
        tradeOrder.setAddress(delivery+tradeOrder.getAddress());
        return tradeOrder;
    }
}
