package com.socialsitebackend.socialsite.user.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class UserRegisterResponseDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime userCreatedDate;
}
