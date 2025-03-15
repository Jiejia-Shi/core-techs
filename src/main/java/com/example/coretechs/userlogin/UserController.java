package com.example.coretechs.userlogin;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.coretechs.userlogin.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public Long addUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        // verify request body
        if (userRegisterRequest == null) {
            return null;
        }

        User user = new User();
        user.setUserName(userRegisterRequest.getUserName());
        user.setPassword(userRegisterRequest.getPassword());
        user.setUserAccount(userRegisterRequest.getUserAccount());
        if (StringUtils.isAnyBlank(user.getUserName(), user.getUserAccount(), user.getPassword())) {
            return null;
        }
        return userService.addUser(user);
    }

    @GetMapping("/current")
    public User getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // update user info, return the updated user info
        return userService.getUserById(currentUser.getId());
    }

    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return userService.userLogout(request);
    }

    @PostMapping("/login")
    public User userLogin(UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        // verify
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }
        return userService.userLogin(userAccount, password, httpServletRequest);
    }


}
