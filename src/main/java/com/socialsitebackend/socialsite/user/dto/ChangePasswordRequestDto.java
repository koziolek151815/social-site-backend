package com.socialsitebackend.socialsite.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangePasswordRequestDto {
    private String oldPassword;
    private String newPassword;
}
