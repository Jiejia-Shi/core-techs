package com.example.coretechs.userlogin;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
@Transactional
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
        final String salt = "JOE";
        String encryptedPassword = DigestUtils.md5DigestAsHex((salt + user.getPassword()).getBytes());

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
}
