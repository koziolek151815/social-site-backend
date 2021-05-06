package com.socialsitebackend.socialsite.user;

import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.user.dto.UserBasicProfileInfoDto;
import com.socialsitebackend.socialsite.user.dto.UserProfileDto;
import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;


@RequiredArgsConstructor
public class UserFactory {

    private final BCryptPasswordEncoder bcryptEncoder;


    public UserEntity registeDtorToEntity(UserRegisterRequestDto dto) {
        return UserEntity.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(bcryptEncoder.encode(dto.getPassword()))
                .userCreatedDate(LocalDateTime.now())
                .gender(dto.getGender())
                .profileDescription(dto.getProfileDescription())
                .avatarUrl(dto.getAvatarUrl())
                .build();
    }

    public UserProfileDto entityToUserProfileDto(UserEntity entity) {
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

    public UserBasicProfileInfoDto entityToBasicUserProfileInfoDto(UserEntity entity) {
        return UserBasicProfileInfoDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .build();
    }
}
