package com.socialsitebackend.socialsite.post.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BasicPostInfoDto {
    private Long postId;
    private String title;
}
