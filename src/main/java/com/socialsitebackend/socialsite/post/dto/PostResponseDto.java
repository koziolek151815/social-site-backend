package com.socialsitebackend.socialsite.post.dto;

import com.socialsitebackend.socialsite.user.dto.UserBasicProfileInfoDto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;


@Builder
@Data
public class PostResponseDto {
    private Long postId;
    private int rating;
    private String title;
    private String description;
    private String postPhotoName;
    private Instant postCreatedDate;
    private UserBasicProfileInfoDto postAuthor;
    private List<String> tags;
    private BasicPostInfoDto parentPost;
}
