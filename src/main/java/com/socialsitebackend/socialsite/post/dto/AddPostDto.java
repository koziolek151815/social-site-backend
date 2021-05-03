package com.socialsitebackend.socialsite.post.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddPostDto {
    private String title;
    private String description;
    private String photoUrl;
}