package com.example.coretechs.userlogin;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    void addUser() {
        String userAccount = "admin";
        String userPassword = "";
        String userName = "joe";
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(userPassword);
        user.setUserName(userName);
        // password is empty
        long result = userService.addUser(user);
        Assertions.assertEquals(-1, result);
        // password is too short
        user.setPassword("123");
        result = userService.addUser(user);
        Assertions.assertEquals(-1, result);
        // normal password
        user.setPassword("123456789");
        result = userService.addUser(user);
        // user account not unique
        Assertions.assertEquals(-1, result);
    }
}