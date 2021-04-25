package com.socialsitebackend.socialsite.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserRequestDto {
    private String username;
    private String password;
    private String email;
}
