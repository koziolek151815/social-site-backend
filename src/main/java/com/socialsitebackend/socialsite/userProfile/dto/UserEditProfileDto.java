package com.socialsitebackend.socialsite.userProfile.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class UserEditProfileDto {
    private String username;
    private String gender;
    private String profileDescription;
}