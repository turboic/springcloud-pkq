package com.turboic.cloud.request;
import com.turboic.cloud.ScanSwaggerObj;
import com.turboic.cloud.pojo.SwaggerUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
/**
 * 测试swagger-ui的controller
 * @author liebe
 */
@Api(value="swagger的controller",tags={"swagger-demo演示接口"})
@RestController
@RequestMapping("/swagger")
@ScanSwaggerObj(value = "使用Swagger-ui的Controller")
public class SwaggerDemoController {
    private static final Logger logger = LoggerFactory.getLogger(SwaggerDemoController.class);


    /**
     * java.lang.IllegalArgumentException: No enum constant org.springframework.web.bind.annotation.RequestMethod.post
     * 解决办法：将httpClient的post小写换成大写
     * @param swaggerUser
     * @return
     */
    @ApiOperation(nickname = "nickname",value="post method", notes="测试post请求", produces="application/json",httpMethod="POST")
    @ApiImplicitParam(name="swaggerUser",value="SwaggerUser实例对象",paramType = "body", dataType = "SwaggerUser", required = true,example = "{\"username\":\"liebe\",\"password\":\"1234567\"}")
    @PostMapping(value = "/v1",produces={MediaType.APPLICATION_JSON_VALUE }, consumes={ MediaType.APPLICATION_JSON_VALUE })
    public SwaggerUser v1(@RequestBody SwaggerUser swaggerUser){
        logger.info("用户名：{}", swaggerUser.getPassword());
        logger.info("密码：{}", swaggerUser.getUsername());
        swaggerUser.setPassword("微信-皮卡丘");
        swaggerUser.setUsername("钉钉-张剑锋");
        logger.info(swaggerUser.toString());
        return swaggerUser;
    }


    /***
     * @ApiImplicitParam：
     * 作用在方法上，表示单独的请求参数
     * 参数：
     * 1. name ：参数名。
     * 2. value ： 参数的具体意义，作用。
     * 3. required ： 参数是否必填。
     * 4. dataType ：参数的数据类型。
     * 5. paramType ：查询参数类型，这里有几种形式：其中path 以地址的形式提交数据；query 直接跟参数完成自动映射赋值；body 以流的形式提交 仅支持POST；header 参数在request headers 里边提交；* form 以form表单的形式提交 仅支持POST
     *
     *
     * 关于dataType的值     *
     *
     * paramType：参数放在哪个地方
     * header-->请求参数的获取：@RequestHeader
     * query-->请求参数的获取：@RequestParam
     * path（用于restful接口）-->请求参数的获取：@PathVariable
     * body（不常用）
     * form（不常用）
     * name：参数名
     * dataType：参数类型
     * required：参数是否必须传
     * value：参数的意思
     * defaultValue：参数的默认值
     * @ApiResponses：用于表示一组响应
     * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息
     * code：数字，例如400
     * message：信息，例如"请求参数没填好"
     * response：抛出异常的类
     * @param username
     * @param password
     * @return
     */
    @ApiOperation(value="用户接口", notes="根据用户名和密码查询用户的信息", produces="application/json",httpMethod="POST")
    @ApiImplicitParams({ @ApiImplicitParam(name="username",value="用户名",paramType = "query", dataType = "String", required = true,example = "admin"),
            @ApiImplicitParam(name="password",value="密码",paramType = "query", dataType = "String", required = true,example = "******")})
    @RequestMapping(value="/v2",method= RequestMethod.POST)
    public SwaggerUser v2(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password) {
        logger.info("用户名：{}，密码：{}", username,password);
        SwaggerUser swaggerUser = new SwaggerUser(username,password);
        return swaggerUser;
    }


    /**
     * 上面的类使用Swagger扫描注解标注
     * 即使在方法上未设置任何swagger-ui相关的设置
     * 在swagger-ui.html还是可以看到相关的内容的
     * @param swaggerUser
     * @return
     */
    @PostMapping(value = "/v3")
    public SwaggerUser v3(@RequestBody SwaggerUser swaggerUser){
        logger.info("用户名：{}", swaggerUser.getPassword());
        logger.info("密码：{}", swaggerUser.getUsername());
        swaggerUser.setPassword("微信");
        swaggerUser.setUsername("钉钉");
        logger.info(swaggerUser.toString());
        return swaggerUser;
    }
}
