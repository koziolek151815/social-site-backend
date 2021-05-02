package com.socialsitebackend.socialsite.factory;

import com.socialsitebackend.socialsite.dto.AddPostDto;
import com.socialsitebackend.socialsite.dto.PostResponseDto;
import com.socialsitebackend.socialsite.model.Post;
import com.socialsitebackend.socialsite.model.User;

import java.time.LocalDateTime;


public class PostFactory {

    private final UserFactory userFactory;

    public PostFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }


    public PostResponseDto entityToResponseDto(Post entity) {
        return PostResponseDto.builder()
                .description(entity.getDescription())
                .photoUrl(entity.getPhotoUrl())
                .postCreatedDate(entity.getPostCreatedDate())
                .title(entity.getTitle())
                .userDto( userFactory.entityToUserProfileDto(entity.getUser()) )
                .build();
    }

    public Post addPostDtoToEntity(AddPostDto dto, User user) {
        return Post.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .photoUrl(dto.getPhotoUrl())

                .postCreatedDate(LocalDateTime.now())
                .user(user)
                .build();

    }
}
