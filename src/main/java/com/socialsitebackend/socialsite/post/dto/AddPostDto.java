package com.socialsitebackend.socialsite.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPostDto {
    private String title;
    private String description;
    private MultipartFile postPhoto;
}