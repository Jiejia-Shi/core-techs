package com.example.coretechs.userlogin;

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
            return -1;
        }
        // user account length should >= 4
        if (user.getUserAccount().length() < 4) {
            return -1;
        }
        // password length should >= 8
        if (user.getPassword().length() < 8) {
            return -1;
        }
        // user account should be unique
        int sameAccountNum = userRepository.countByUserAccount(user.getUserAccount());
        if (sameAccountNum > 0) {
            return -1;
        }

        // encrypt
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + user.getPassword()).getBytes());

        // add data
        user.setPassword(encryptedPassword);
        userRepository.save(user);
        Long userId = user.getId();
        if (userId == null) {
            return -1;
        } else {
            return userId;
        }
    }

    public User userLogin(String userAccount, String password, HttpServletRequest request) {
        // verify userAccount and password
        if(StringUtils.isAnyBlank(userAccount, password)) {
            return null;
        }
        // user account length should >= 4
        if (userAccount.length() < 4) {
            return null;
        }
        // password length should >= 8
        if (password.length() < 8) {
            return null;
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
            return null;
        }

    }

    public User getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        return convertToSafeUser(user);
    }

    public User convertToSafeUser(User user) {
        if (user == null) {
            return null;
        }
        User safeUser = new User();
        safeUser.setUserAccount(user.getUserAccount());
        safeUser.setUserType(user.getUserType());
        safeUser.setUserName(user.getUserName());
        safeUser.setId(user.getId());
        return safeUser;
    }
}
