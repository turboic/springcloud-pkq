package com.turboic.cloud.controller;

import com.turboic.cloud.entity.User;
import com.turboic.cloud.util.FastJsonUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/json")
public class JSONController {

    @PostMapping(value="/post{app}")
    public String post(@RequestBody User user,@PathVariable(value="app") String app,String address){
        user.setPassword(address);
        user.setUsername(app);
        return FastJsonUtils.objectToJson(user);
    }
}
