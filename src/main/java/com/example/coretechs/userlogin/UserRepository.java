package com.example.coretechs.userlogin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public int countByUserAccount(String userAccount);
}
