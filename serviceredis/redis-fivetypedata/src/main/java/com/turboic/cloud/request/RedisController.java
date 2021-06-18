package com.turboic.cloud.request;
import com.turboic.cloud.ScanSwaggerObj;
import com.turboic.cloud.service.impl.RedisService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 测试redis的controller
 * @author liebe
 */
@Api(value="redis 操作的controller",tags={"redis操作的演示"})
@RestController
@RequestMapping("/redis")
@ScanSwaggerObj(value = "使用Swagger-RedisController")
@Slf4j
public class RedisController {
    private final RedisService redisService;
    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    /***
     *
     * @param redisKey
     * @param redisValue
     * @return
     */
    @PostMapping(value = "/v1")
    public String v1(String redisKey,String redisValue){
        log.info("redisKey：{}",redisKey);
        log.info("redisValue：{}",redisValue);
        return redisService.redisString(redisKey,redisValue);
    }
}
