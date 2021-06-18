package com.turboic.cloud;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SentinelControllerTest {
    @Test
    public void contextLoads() {
        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 10; i++) {
            String s = restTemplate.getForObject("http://127.0.0.1:8081/exec/"+i, String.class);
            System.out.println(s + ":" + LocalDateTime.now().toString());
        }
    }
}
