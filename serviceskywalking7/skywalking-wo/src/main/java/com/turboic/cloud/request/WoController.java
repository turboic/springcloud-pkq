package com.turboic.cloud.request;

import com.turboic.cloud.feign.IFeignPlatformService;
import com.turboic.cloud.pojo.TradeOrder;
import com.turboic.cloud.pojo.WoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liebe
 */
@RestController
@RequestMapping("/skywalking-wo")
@Slf4j
public class WoController {

    private final IFeignPlatformService iFeignPlatformService;

    /**
     * 这里注入类型失败，找不到bean，可能是未开启fegin功能
     * @param iFeignPlatformService
     */
    @Autowired
    public WoController(IFeignPlatformService iFeignPlatformService){
        this.iFeignPlatformService = iFeignPlatformService;
    }

    @RequestMapping("/dingwaimai")
    public String dingwaimai(@RequestBody WoUser woUser){
        log.error("通过微服务请求,获取用户信息:{}",woUser.toString());
        /**
         * 调用skywalking-meituan的微服务接口，微服务之间的调用
         */
        TradeOrder tradeOrder = iFeignPlatformService.purchase(woUser);

        log.error("通过meituan微服务生成订单信息,获取用户信息:{}",tradeOrder.toString());

        return tradeOrder.toString();
    }
}
