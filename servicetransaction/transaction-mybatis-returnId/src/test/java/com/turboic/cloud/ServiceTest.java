package com.turboic.cloud;
import com.turboic.cloud.entity.User;
import com.turboic.cloud.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testInsertReturnId1(){
        User user = new User();
        user.setName("张");
        user.setSex("男");
        user.setBirthday(new Date());
        user.setMobile("1415217423273");
        user.setAddress("北京市");
        int result = userService.insertUseGeneratedKeyAsId(user);
        System.err.println("userService.insertUseGeneratedKeyAsId(user):"+result);
        System.err.println("此时数据的主键是:"+user.getId());
    }

    @Test
    public void testInsertReturnId2(){
        User user = new User();
        user.setName("杨");
        user.setSex("女");
        user.setBirthday(new Date());
        user.setMobile("18301333333");
        user.setAddress("深圳市");
        int result = userService.insertLastInsertId(user);
        System.err.println("userService.insertLastInsertId(user):"+result);
        System.err.println("2此时数据的主键是:"+user.getId());
    }
}
