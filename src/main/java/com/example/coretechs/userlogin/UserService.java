package com.example.coretechs.userlogin;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean addUser(User user) {
        userRepository.save(user);
        return true;
    }
}
