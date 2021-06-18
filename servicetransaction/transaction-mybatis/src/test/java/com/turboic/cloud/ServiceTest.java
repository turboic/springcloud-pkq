package com.turboic.cloud;
import com.turboic.cloud.entity.Liebe;
import com.turboic.cloud.service.LiebeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.UUID;
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServiceTest {
    @Autowired
    private LiebeService liebeService;
    public void save(){
        Liebe liebe = new Liebe();
        liebe.setId(UUID.randomUUID().toString());
        liebe.setUsername("admin");
        liebe.setPassword("admin");
        boolean save = liebeService.save(liebe);
        if(save){
            log.debug("数据保存成功");
        }
        else{
            log.error("数据保存失败");
        }
    }
}
