package com.socialsitebackend.socialsite.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginRequestDto {
    private String username;
    private String password;
}
