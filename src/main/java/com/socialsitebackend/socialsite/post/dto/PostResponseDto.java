package com.socialsitebackend.socialsite.post.dto;

import com.socialsitebackend.socialsite.user.dto.UserBasicProfileInfoDto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Builder
@Data
public class PostResponseDto {
    private Long postId;
    private int rating;
    private String title;
    private String description;
    private String postPhotoName;
    private LocalDateTime postCreatedDate;
    private UserBasicProfileInfoDto postAuthor;
    private BasicPostInfoDto parentPost;
}
