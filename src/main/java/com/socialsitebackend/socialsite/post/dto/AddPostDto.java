package com.socialsitebackend.socialsite.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddPostDto {
    private String title;
    private List<String> tags;
    private String description;
    private MultipartFile postPhoto;
}