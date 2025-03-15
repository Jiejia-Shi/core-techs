package com.example.coretechs.userlogin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Data
@Schema
public class UserRegisterRequest implements Serializable {

    @Schema
    private String userName;

    @Schema
    private String userAccount;

    @Schema
    private String password;
}
