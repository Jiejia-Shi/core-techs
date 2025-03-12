package com.example.coretechs.userlogin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Schema
public class UserDto {

    @Schema
    private String userName;

    @Schema
    private String password;
}
