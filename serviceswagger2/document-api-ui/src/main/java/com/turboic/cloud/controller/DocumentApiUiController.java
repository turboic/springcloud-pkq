package com.turboic.cloud.controller;
import com.turboic.cloud.pojo.AlaDing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

/**
 * @author liebe
 */
@RestController
@RequestMapping(value = "/api")
public class DocumentApiUiController
{
    private static final Logger logger = LoggerFactory.getLogger(DocumentApiUiController.class);

    @GetMapping(value = "/get")
    public AlaDing getAlaDing(){
        logger.error("请求了DocumentApiUiController的get方法");
        AlaDing alaDing = new AlaDing("阿拉丁与神灯的故事","2020年",new Date());
        return alaDing;
    }

    @PostMapping(value = "/post")
    public AlaDing post(@RequestBody AlaDing alaDing){
        logger.error("请求了DocumentApiUiController的post方法");
        alaDing.setName("微信拍一拍");
        return alaDing;
    }
}
