package com.turboic.cloud.provider;

import com.turboic.cloud.example.RestRoute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

/**
 * @author liebe
 */
@RestController
public class ProviderController {
    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    private static final String[] PETS = new String[]{"地铁","老虎", "狮子", "小熊猫", "猫咪", "泰迪"};

    @GetMapping(value = "/pets/{id}")
    public Map<String, String> petById(@PathVariable("id") Integer id) {
        logger.error("RestProviderController收到请求:{}",id);
        if (id != null && id > 0 && id <= PETS.length) {
            int index = id - 1;
            String pet = PETS[index];
            return Collections.singletonMap("name", pet);
        } else {
            return Collections.emptyMap();
        }
    }

    @RequestMapping(value = "/")
    public String index() {
        logger.error("当您看到此处日志有信息输出，说明route开始加载初始化了");
        return "为了避免出现连接拒绝的不友好信息，所以加了个默认的信息打印处理";
    }

}