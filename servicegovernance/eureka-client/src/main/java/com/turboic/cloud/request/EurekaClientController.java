package com.turboic.cloud.request;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.discovery.DiscoveryManager;
import com.netflix.discovery.endpoint.EndpointUtils;
import com.turboic.cloud.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

    @Autowired
    private DiscoveryClient discoveryClient;


    @GetMapping("/list")
    public String list(){
        List<String> nameList = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(nameList)) {
            return "no eureka client instance !!!";
        }
        nameList.forEach(n->{
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(n);

        });

        DiscoveryManager.getInstance().shutdownComponent();

        return "success";
    }
}
