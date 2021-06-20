package com.socialsitebackend.socialsite.user;

import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.user.dto.UserBasicProfileInfoDto;
import com.socialsitebackend.socialsite.userProfile.dto.UserProfileDto;
import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;

import com.socialsitebackend.socialsite.user.dto.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.Instant;


@RequiredArgsConstructor
public class UserFactory {

    private final BCryptPasswordEncoder bcryptEncoder;


    public UserEntity registeDtorToEntity(UserRegisterRequestDto dto) {
        return UserEntity.builder()
                .email(dto.getEmail())
                .username(dto.getUsername())
                .password(bcryptEncoder.encode(dto.getPassword()))
                .userCreatedDate(Instant.now())
                .gender(dto.getGender() == null ? "" : dto.getGender())
                .profileDescription(dto.getProfileDescription() == null ? "" : dto.getProfileDescription())
                .avatarUrl(dto.getAvatarUrl() == null ? "" : dto.getAvatarUrl())
                .userActive(true)
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

    public UserRegisterResponseDto entityToRegisterResponseDto(UserEntity entity) {
        return UserRegisterResponseDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .username(entity.getEmail())
                .userCreatedDate(entity.getUserCreatedDate())
                .build();
    }

    public UserBasicProfileInfoDto entityToBasicUserProfileInfoDto(UserEntity entity) {
        return UserBasicProfileInfoDto.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .userActive(entity.getUserActive())
                .build();
    }
}
