package com.turboic.cloud.controller;
import com.turboic.cloud.config.Swagger2Controller;
import com.turboic.cloud.entity.Liebe;
import com.turboic.cloud.service.LiebeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author liebe
 * 测试mysql数据库的保存操作
 */
@Api(value="swagger的swagger-controller-mybatis-transaction",tags={"swagger-controller-mybatis-transaction演示接口"})
@RestController
@RequestMapping("/mybatis")
@Swagger2Controller(value = "使用Swagger-ui的Controller")
public class LiebeController {
    private static final Logger log = LoggerFactory.getLogger(LiebeController.class);

    @Autowired
    private LiebeService liebeService;
    /**
     * java.lang.IllegalArgumentException: No enum constant org.springframework.web.bind.annotation.RequestMethod.post
     * 解决办法：将httpClient的post小写换成大写
     * @param liebe
     * @return
     */
    @ApiOperation(nickname = "nickname",value="post method", notes="测试post请求", produces="application/json",httpMethod="POST")
    @ApiImplicitParam(name="liebe",value="Liebe实例对象",paramType = "body", dataType = "Liebe", required = true,example = "{\"id\":\"123456\",\"username\":\"liebe\",\"password\":\"1234567\"}")
    @PostMapping(value = "/transaction",produces={MediaType.APPLICATION_JSON_VALUE }, consumes={ MediaType.APPLICATION_JSON_VALUE })
    public String  transaction(@RequestBody Liebe liebe){
        log.info("用户名：{}", liebe.getPassword());
        log.info("密码：{}", liebe.getUsername());
        liebeService.processing(liebe);
        return "mybatis 数据transaction 处理成功";
    }
}
