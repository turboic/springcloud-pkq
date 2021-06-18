package com.turboic.cloud.request;
import com.turboic.cloud.ScanSwaggerObj;
import com.turboic.cloud.redis.RedisUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试redis的controller
 * @author liebe
 */
@Api(value="redis 操作的controller",tags={"redis测试操作的演示"})
@RestController
@RequestMapping("/test")
@ScanSwaggerObj(value = "使用Swagger-RedisController")
@Slf4j
public class TestController {

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);

    private final RedisUtil redisUtil;
    @Autowired
    public TestController(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    @GetMapping(value = "/v1")
    public String v1(HttpServletRequest request, HttpServletResponse response){
        String remoteAddr = request.getRemoteAddr();
        if(StringUtils.isEmpty(remoteAddr)){
            return "非法访问";
        }
        logger.info("remoteAddr:{}",remoteAddr);
        Object value = redisUtil.get(remoteAddr);
        logger.info("value:{}",value);
        if(value == null){
            redisUtil.redisPersistString(remoteAddr,1);
        }
        else{
            Integer count = (Integer) value;
            count+=1;
            redisUtil.redisPersistString(remoteAddr,count);
        }
        Integer num = (Integer) value;
        if(num != null && num  > 10){
            return "访问次数过多,调用接口被限制";
        }
        String token = request.getParameter("token");
        return "hello "+token;
    }
}
