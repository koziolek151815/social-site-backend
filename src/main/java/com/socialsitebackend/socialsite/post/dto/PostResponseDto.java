package com.socialsitebackend.socialsite.post.dto;

import com.socialsitebackend.socialsite.user.dto.UserProfileDto;
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
