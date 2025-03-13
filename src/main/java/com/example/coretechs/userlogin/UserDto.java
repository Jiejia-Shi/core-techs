package com.example.coretechs.userlogin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema
public class UserDto {

    @Schema
    private String userName;

    @Schema
    private String userAccount;

    @Schema
    private String password;
}
