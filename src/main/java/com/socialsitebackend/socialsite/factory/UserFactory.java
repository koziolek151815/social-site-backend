package com.socialsitebackend.socialsite.factory;


import com.socialsitebackend.socialsite.dto.UserProfileDto;
import com.socialsitebackend.socialsite.dto.UserRegisterRequestDto;
import com.socialsitebackend.socialsite.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

public class UserFactory {

    private final BCryptPasswordEncoder bcryptEncoder;

    public UserFactory(BCryptPasswordEncoder bcryptEncoder) {
        this.bcryptEncoder = bcryptEncoder;
    }


    public User registeDtorToEntity(UserRegisterRequestDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(bcryptEncoder.encode(dto.getPassword()))

                .userCreatedDate(LocalDateTime.now())
                .gender(dto.getGender())
                .profileDescription(dto.getProfileDescription())
                .avatarUrl(dto.getAvatarUrl())

                .build();
    }

    public UserProfileDto entityToUserProfileDto(User entity) {
        return UserProfileDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getUsername())

                .userCreatedDate(entity.getUserCreatedDate())
                .gender(entity.getGender())
                .profileDescription(entity.getProfileDescription())
                .avatarUrl(entity.getAvatarUrl())
                .build();
    }
}
