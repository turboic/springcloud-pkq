package com.turboic.cloud.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试swagger-ui的controller
 * @author liebe
 */
@Api(value="swagger的controller",tags={"swagger-demo演示接口"})
@RestController
@RequestMapping("/zk")
public class ZookeeperController {
    private static final Logger logger = LoggerFactory.getLogger(ZookeeperController.class);
    @Autowired
    private DiscoveryClient discoveryClient;
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
    @ApiOperation(value="用户接口", notes="根据用户名和密码查询用户的信息", produces="application/json",httpMethod="GET")
    @ApiImplicitParams({ @ApiImplicitParam(name="username",value="用户名",paramType = "query",
            dataType = "String", required = true,example = "admin"),
            @ApiImplicitParam(name="password",value="密码",paramType = "query", dataType = "String",
                    required = true,example = "******")})
    @RequestMapping(value="/v2",method= RequestMethod.GET)
    public String v2(@RequestParam(value = "username") String username,@RequestParam(value = "password") String password) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(discoveryClient.description());
        stringBuilder.append(discoveryClient.getOrder());
        List<String> services = discoveryClient.getServices();
        if(services != null && !services.isEmpty()){
            stringBuilder.append("services:{");
            int i = 0;
            for(String service : services){
                i++;
                stringBuilder.append(service);
                if(i < services.size()){
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append("}");
        }

        stringBuilder.append("hello:"+username).append("your password:"+password);
        logger.info("stringBuilder:{}",stringBuilder.toString());
        return stringBuilder.toString();
    }
}
