package com.socialsitebackend.socialsite.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PostResponseDto {

    private String title;
    private String description;
    private String photoUrl;
    private LocalDateTime postCreatedDate;
    private UserProfileDto userDto;
}
