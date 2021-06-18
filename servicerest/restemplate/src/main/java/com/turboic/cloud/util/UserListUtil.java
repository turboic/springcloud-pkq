package com.turboic.cloud.util;

import com.turboic.cloud.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liebe
 */
@Component
public class UserListUtil {
    private final List<User> userList;

    public UserListUtil(){
        userList = new ArrayList<>();
        User user = new User();
        user.setPassword("admin");
        user.setUsername("admin");
        user.setLoginDate(new Date());

        User user2 = new User();
        user2.setPassword("北京");
        user2.setUsername("北京");
        user2.setLoginDate(new Date());
        userList.add(user);
        userList.add(user2);
    }


    public List<String> showInfo(){
        List<String> nameList = Optional.ofNullable(userList).map(u->userList.parallelStream().map(
                User::getUsername).collect(Collectors.toList())).orElse(Collections.emptyList());
        return nameList;
    }
}
