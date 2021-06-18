package com.turboic.cloud.service.impl;
import com.turboic.cloud.service.ProviderServiceService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
/**
 * @author liebe
 */
@Service
public class ProviderServiceServiceImpl implements ProviderServiceService {
    @Override
    public String sayHello(String name) {
        if(StringUtils.isEmpty(name)){
            name = "hello ,我忘记了你的名字,How old are you";
        }
        return "hello "+name;
    }
}
