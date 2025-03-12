package com.example.coretechs.userlogin;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public void addUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        userService.addUser(user);
    }
}
