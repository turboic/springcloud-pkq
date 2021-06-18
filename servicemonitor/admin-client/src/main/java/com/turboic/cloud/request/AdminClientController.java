package com.turboic.cloud.request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;

/**
 * @author liebe
 */


@RestController
@RequestMapping("/admin-client")
public class AdminClientController {

    private final Environment environment;

    @Autowired
    public AdminClientController(Environment environment) {
        this.environment = environment;
    }

    @RequestMapping("/info")
    public String defaultReturnInfo(){
        String [] activeProfiles = environment.getActiveProfiles();
        StringBuffer stringBuffer = new StringBuffer();
        Arrays.stream(activeProfiles).forEach(a->stringBuffer.append(a));
        return stringBuffer.toString();
    }
}
