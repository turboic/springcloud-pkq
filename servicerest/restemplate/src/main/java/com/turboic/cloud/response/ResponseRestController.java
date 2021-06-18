package com.turboic.cloud.response;
import com.turboic.cloud.entity.User;
import com.turboic.cloud.util.UserListUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * jpa-service的controller
 * @author liebe
 */
@Api(value="jpa transaction invoke的controller",tags={"jpa事务的接口"})
@RestController
@RequestMapping("/response")
@Slf4j
public class ResponseRestController {
    private static final Logger logger = LoggerFactory.getLogger(ResponseRestController.class);

    @RequestMapping("/getForObject")
    public String getForObject(@RequestParam(value = "name") String name){
        logger.error("参数name:{}",name);
        return "hello" +name;
    }


    @PostMapping("/postForEntity")
    public User postForEntity(String username,String password){
        User user = new User();
        user.setLoginDate(new Date());
        user.setUsername(username);
        user.setPassword(password);
        logger.error("ResponseRestController.postForEntity.user:{}",user.toString());
        return user;
    }

    @DeleteMapping("/delete/{username}/{password}")
    public String delete(@PathVariable(value="username") String username,@PathVariable(value="password") String password){
        logger.error("username:{},password:{}",username,password);
        return "delete方法响应成功";
    }


    @RequestMapping("/exchange/{username}/{password}")
    public List<String> exchange(@PathVariable(value="username") String username,
                                 @PathVariable(value="password") String password,
                                 String fileId,String fileName){
        log.error("username:{}",username);
        log.error("password:{}",password);
        log.error("fileId:{}",fileId);
        log.error("fileName:{}",fileName);
        List<String> list = new ArrayList<>();
        list.add(username);
        list.add(password);
        list.add(fileId);
        list.add(fileName);
        return list;
    }


    @RequestMapping("/execute/{username}/{password}")
    public String execute(@PathVariable(value="username") String username,
                                 @PathVariable(value="password") String password,
                                 String server,String client){
        log.error("username:{}",username);
        log.error("password:{}",password);
        log.error("server:{}",server);
        log.error("client:{}",client);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(username).append(",");
        stringBuilder.append(password).append(",");
        stringBuilder.append(server).append(",");
        stringBuilder.append(client).append(",");
        stringBuilder.delete(stringBuilder.length()-1,stringBuilder.length());
        return stringBuilder.toString();
    }


    @Autowired
    private UserListUtil userListUtil;

    @RequestMapping("/user")
    public String user(){
        List<String> infoList =  userListUtil.showInfo();
        Optional.ofNullable(infoList).ifPresent(System.out::println);
        StringBuilder stringBuilder = new StringBuilder();
        infoList.parallelStream().filter(i-> !StringUtils.isEmpty(i) && i.equals("admin")).collect(Collectors.toList()).forEach(i->stringBuilder.append(i));
        return stringBuilder.toString();
    }

}
