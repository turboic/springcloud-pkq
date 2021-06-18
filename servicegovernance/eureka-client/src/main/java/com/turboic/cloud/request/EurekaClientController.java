package com.turboic.cloud.request;

import com.turboic.cloud.pojo.User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liebe
 */

@RestController
@RequestMapping("/eureka-client")
public class EurekaClientController {
    private Map<String,String> map = new HashMap<>();

    @PutMapping("/put")
    public Map putMethod(@RequestParam(value="name") String name){
        if(StringUtils.isEmpty(name)){
            name = "liebe";
        }
        map.put("name",name);
        return map;
    }


    @GetMapping("/get/{name}")
    public String getMethod(@PathVariable(value = "name") String name){
        if(StringUtils.isEmpty(name)){
            name = "liebe";
        }
        return name;
    }

    @PostMapping("/post")
    public User postMethod(@RequestBody User user){
        if(user == null){
            user = new User();
            user.setName("liebe");
            user.setPassword("123456");
        }
        return user;
    }


    @DeleteMapping("/delete")
    public User deleteMethod(String name){
        User user = new User();
        user.setName(name);
        user.setPassword("123456");
        return user;
    }
}
