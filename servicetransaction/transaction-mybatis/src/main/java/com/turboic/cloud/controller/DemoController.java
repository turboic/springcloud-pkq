package com.turboic.cloud.controller;

import com.turboic.cloud.entity.Liebe;
import com.turboic.cloud.service.LiebeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author liebe
 */
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    private final LiebeService liebeService;

    public DemoController(LiebeService liebeService) {
        this.liebeService = liebeService;
    }

    @GetMapping(value="/direct")
    public String direct(){
        long start = System.currentTimeMillis();
        return (System.currentTimeMillis() - start) +"";
    }

    @GetMapping(value="/db")
    public String db(){
        long start = System.currentTimeMillis();
        Liebe liebe = null;

        liebe.setId(UUID.randomUUID().toString());
        liebe.setUsername("username");
        liebe.setPassword("password");
        liebe.setCreatetime(new Timestamp(System.currentTimeMillis()));
        liebe.setUpdatetime(new Timestamp(System.currentTimeMillis()));
        liebeService.save(liebe);
        return (System.currentTimeMillis() - start) +":("+liebe.toString();
    }

    @GetMapping(value="/tx")
    public String tx(){
        long start = System.currentTimeMillis();
        Liebe liebe = new Liebe();
        liebeService.saveData(liebe);
        return (System.currentTimeMillis() - start) +"ms--------:("+liebe.toString();
    }
}
