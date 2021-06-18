package com.turboic.cloud.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liebe
 */
@RestController(value = "/provider")
public class SentinelController {

    @GetMapping("/openFeign/{param}")
    public String cloud(@PathVariable(value = "param") String param) {
        System.out.println("执行微服务sentinel-nacos-provider的方法openFeign（"+param+")");
        return "执行微服务sentinel-nacos-provider的方法openFeign（"+param+")";
    }


    @GetMapping("/post-verify/{parameter}")
    public String post(@PathVariable(value = "parameter") String parameter) {
        System.out.println("执行微服务sentinel-nachos-provider的方法openFeign（"+parameter+")");
        return "执行微服务sentinel-nachos-provider的方法openFeign（"+parameter+")";
    }






}
