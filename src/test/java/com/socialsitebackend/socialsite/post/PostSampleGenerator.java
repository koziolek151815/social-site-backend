package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.RandomDataGenerator;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;

import java.time.LocalDateTime;


public class PostSampleGenerator {
    public static AddPostDto getSampleAddPostDto() {
        return AddPostDto.builder()
                .title(RandomDataGenerator.getRandomString())
                .description(RandomDataGenerator.getRandomString())
                .photoUrl(RandomDataGenerator.getRandomString())
                .build();
    }

    public static PostResponseDto getRandomPostResponseDto() {
        return PostResponseDto.builder()
                .postId(RandomDataGenerator.getRandomLong())
                .postAuthor(RandomDataGenerator.getRandomUserBasicProfileDto())
                .postCreatedDate(LocalDateTime.now())
                .title(RandomDataGenerator.getRandomString())
                .description(RandomDataGenerator.getRandomString())
                .photoUrl(RandomDataGenerator.getRandomString())
                .build();
    }


}
