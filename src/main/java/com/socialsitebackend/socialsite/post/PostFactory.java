package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.PostEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.user.UserFactory;

import java.time.LocalDateTime;


public class PostFactory {

    private final UserFactory userFactory;

    public PostFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }


    public PostResponseDto entityToResponseDto(PostEntity entity) {
        return PostResponseDto.builder()
                .postId(entity.getPostId())
                .description(entity.getDescription())
                .photoUrl(entity.getPhotoUrl())
                .postCreatedDate(entity.getPostCreatedDate())
                .title(entity.getTitle())
                .postAuthor( userFactory.entityToBasicUserProfileInfoDto(entity.getPostAuthor()) )
                .build();
    }

    public PostEntity addPostDtoToEntity(AddPostDto dto, UserEntity postAuthor) {
        return PostEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .photoUrl(dto.getPhotoUrl())

                .postCreatedDate(LocalDateTime.now())
                .postAuthor(postAuthor)
                .build();

    }
}
