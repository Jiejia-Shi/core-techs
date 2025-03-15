package com.example.coretechs.userlogin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema
public class UserLoginRequest implements Serializable {

    @Schema
    private String userAccount;

    @Schema
    private String password;
}
