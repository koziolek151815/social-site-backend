package com.socialsitebackend.socialsite.user.dto;


import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class DeactivateUserRequestDto {
    private String password;
}
