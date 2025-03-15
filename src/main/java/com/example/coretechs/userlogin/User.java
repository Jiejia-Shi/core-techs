package com.example.coretechs.userlogin;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Data
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    // login account
    @Column(unique = true, nullable = false)
    private String userAccount;

    // login password
    @Column(nullable = false)
    private String password;

    @Column
    private String userType;
}
