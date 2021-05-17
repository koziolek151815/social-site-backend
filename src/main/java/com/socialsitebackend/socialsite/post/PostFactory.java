package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.PostEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.BasicPostInfoDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.user.UserFactory;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@RequiredArgsConstructor
public class PostFactory {

    private final UserFactory userFactory;


    public PostResponseDto entityToResponseDto(PostEntity entity) {
        return PostResponseDto.builder()
                .postId(entity.getId())
                .description(entity.getDescription())
                .photoUrl(entity.getPhotoUrl())
                .postCreatedDate(entity.getPostCreatedDate())
                .title(entity.getTitle())
                .postAuthor(userFactory.entityToBasicUserProfileInfoDto(entity.getAuthor()))
                .parentPost(this.entityToBasicPostInfoDto(entity.getParentPost()))
                .rating(entity.getVotes() == null
                        ? 0
                        : entity.getVotes().stream().map(vote -> vote.getRating() ? 1 : -1).reduce(0, Integer::sum))
                .build();
    }

    public PostEntity addPostDtoToEntity(AddPostDto dto, UserEntity postAuthor) {
        return PostEntity.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .photoUrl(dto.getPhotoUrl())
                .postCreatedDate(LocalDateTime.now())
                .author(postAuthor)
                .parentPost(parentPost)
                .build();
    }

    public BasicPostInfoDto entityToBasicPostInfoDto(PostEntity post){
        if (post == null) return null;
        return BasicPostInfoDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .build();
    }
}
