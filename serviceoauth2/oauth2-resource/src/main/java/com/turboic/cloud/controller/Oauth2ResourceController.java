package com.turboic.cloud.controller;
import com.alibaba.fastjson.JSON;
import com.turboic.cloud.util.HttpRequestUtil;
import com.turboic.cloud.util.TokenResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 测试oauth-client的controller
 * @author liebe
 */
@Api(value="oauth-client的controller",tags={"oauth-client演示接口"})
@RestController
@RequestMapping("/resource")
public class Oauth2ResourceController {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2ResourceController.class);


    private final DiscoveryClient discoveryClient;

    public Oauth2ResourceController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }


    @PostMapping(value = "/oauth2")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public Object get(Authentication authentication){
        logger.error("进入授权方法oauth2");
        Authentication authentication1= SecurityContextHolder.getContext().getAuthentication();
        authentication.getCredentials();
        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails)authentication.getDetails();
        return JSON.toJSONString(details);
    }

    @Autowired
    private RestTemplate restTemplate;
    @GetMapping(value = "/getToken")
    public Object getToken(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        logger.error("进入生成token的方法getToken:{}",code);
        String url = "http://localhost:3002/oauth/token?grant_type=authorization_code&client_id=resourceApp&client_secret=resourceApp&code="+code+"&redirect_uri=http://localhost:3003/login/auth";
        logger.info("请求token生成地址:{}",url);
        try {
            String post = "http://localhost:3002/oauth/token";
            Map<String, String> headers = new HashMap<>(7);
            Map<String, String> params = new HashMap<>(4);
            params.put("grant_type","authorization_code");
            params.put("client_id","resourceApp");
            params.put("client_secret","resourceApp");
            params.put("code",code);
            params.put("redirect_uri","http://localhost:3003/login/auth");
            Response res = HttpRequestUtil.INSTANCE.post(post,headers,params,null);
            String token = null;
            if(response != null && res.body() != null){
                okhttp3.ResponseBody responseBody = res.body();
                String responseBodyString = responseBody.string();
                logger.info("接口返回结果:{}",responseBodyString);
                TokenResult tokenResult = JSON.parseObject(responseBodyString, TokenResult.class);
                token = tokenResult.getAccess_token();
                logger.info("接口返回token:{}",token);
            }
            logger.info("即将进行页面的跳转啊");
            ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:3003/resource/oauth2", getRequest(request,token), String.class);
            if(request == null){
                return "请求微服务地址不成功，返回为空";
            }
            String text = result.getBody();
            if(StringUtils.isEmpty(text)){
                return "请求微服务地址成功，返回结果为空";
            }
            logger.info(text);
            return text;
        } catch (IOException e) {
            logger.error("IOException:{}",e);
            return e.getCause();
        }
    }

    private HttpEntity<?> getRequest(HttpServletRequest request,String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return new HttpEntity<>(null, headers);
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
    @ApiOperation(value="用户接口", notes="根据用户名和密码查询用户的信息", produces="application/json",httpMethod="GET")
    @ApiImplicitParams({ @ApiImplicitParam(name="username",value="用户名",paramType = "query",
            dataType = "String", required = true,example = "admin"),
            @ApiImplicitParam(name="password",value="密码",paramType = "query", dataType = "String",
                    required = true,example = "******")})
    @RequestMapping(value="/get",method= RequestMethod.GET)
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
