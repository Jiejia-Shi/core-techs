package com.example.coretechs.userlogin;


import com.example.coretechs.common.BaseResponse;
import com.example.coretechs.common.ResultUtils;
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
    public BaseResponse<Long> addUser(@RequestBody UserRegisterRequest userRegisterRequest) {
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
        Long result = userService.addUser(user);
        return ResultUtils.success(result);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            return null;
        }
        // update user info, return the updated user info
        User result = userService.getUserById(currentUser.getId());
        return ResultUtils.success(result);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Integer result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        // verify
        if (userLoginRequest == null) {
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String password = userLoginRequest.getPassword();
        if (StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }
        User result = userService.userLogin(userAccount, password, httpServletRequest);
        return ResultUtils.success(result);
    }


}
