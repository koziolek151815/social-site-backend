package com.socialsitebackend.socialsite.user.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginRequestDto {
    private String email;
    private String password;
}
