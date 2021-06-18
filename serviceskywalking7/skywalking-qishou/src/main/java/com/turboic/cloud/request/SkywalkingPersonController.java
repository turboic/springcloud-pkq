package com.turboic.cloud.request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liebe
 */
@RestController
@RequestMapping("/person")
@Slf4j
public class SkywalkingPersonController {
    @PostMapping("/delivery")
    public String delivery(@RequestParam(value = "orderNumber") String orderNumber){
        log.info("骑手收到配送订单信息:{}",orderNumber);
        return orderNumber+",距您2.7km 17分钟";
    }
}
