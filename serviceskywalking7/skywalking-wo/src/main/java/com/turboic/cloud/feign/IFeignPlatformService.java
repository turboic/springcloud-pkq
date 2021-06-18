package com.turboic.cloud.feign;
import com.turboic.cloud.pojo.TradeOrder;
import com.turboic.cloud.pojo.WoUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * @author Liebe
 */
@FeignClient(name="skywalking-meituan",fallbackFactory = IFeignPlatformServiceFallbackFactory.class)
public interface IFeignPlatformService {
    @RequestMapping(value = "/platform/trade", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    /**
     * 购买商品的接口
     */
    TradeOrder purchase(WoUser woUser);
}