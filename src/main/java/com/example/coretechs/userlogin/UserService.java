package com.example.coretechs.userlogin;

import com.example.coretechs.common.ErrorCode;
import com.example.coretechs.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Optional;

import static com.example.coretechs.userlogin.UserConstant.USER_LOGIN_STATE;

@Service
@Transactional
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static final String SALT = "JOE";

    public long addUser(User user) {
        // verify data
        if (StringUtils.isAnyBlank(user.getUserName(), user.getUserAccount(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_NULL, "user name, account and password are required");
        }
        // user account length should >= 4
        if (user.getUserAccount().length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "user account length less than 4");
        }
        // password length should >= 8
        if (user.getPassword().length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "password length less than 8");
        }
        // user account should be unique
        int sameAccountNum = userRepository.countByUserAccount(user.getUserAccount());
        if (sameAccountNum > 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "user account already exists");
        }

        // encrypt
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + user.getPassword()).getBytes());

        // add data
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        Long userId = user.getId();
        if (userId == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "saving user failed: can't get id");
        } else {
            return userId;
        }
    }

    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        // verify userAccount and password
        if(StringUtils.isAnyBlank(userAccount, password)) {
            throw new BusinessException(ErrorCode.PARAMS_NULL, "user account, password are required");
        }
        // user account length should >= 4
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "user account length less than 4");
        }
        // password length should >= 8
        if (password.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "password length less than 8");
        }

        // encrypt
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes());

        // find in database
        Specification<User> spec = (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("userAccount"), userAccount),
                        criteriaBuilder.equal(root.get("password"), encryptedPassword)
                );

        Optional<User> userOptional = userRepository.findOne(spec);

        // record session
        if (userOptional.isPresent()) {
            // data desensitization
            User safeUser = convertToSafeUser(userOptional.get());
            // save session attribute
            request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);
            return safeUser;
        } else {
            throw new BusinessException(ErrorCode.NO_AUTH, "account and password do not match");
        }

    }

    public Integer userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    public User getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        return convertToSafeUser(user);
    }

    public User convertToSafeUser(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "convert failed: user is null");
        }
        User safeUser = new User();
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setUserType(user.getUserType());
        safeUser.setUserName(user.getUserName());
        safeUser.setId(user.getId());
        return safeUser;
    }
}
