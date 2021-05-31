package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.PostEntity;
import com.socialsitebackend.socialsite.entities.TagEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.BasicPostInfoDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.user.UserFactory;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class PostFactory {

    private final UserFactory userFactory;



    public PostResponseDto entityToResponseDto(PostEntity entity) {
        return PostResponseDto.builder()
                .postId(entity.getId())
                .description(entity.getDescription())
                .postPhotoName(entity.getPostPhotoName())
                .postCreatedDate(entity.getPostCreatedDate())
                .title(entity.getTitle())
                .postAuthor(userFactory.entityToBasicUserProfileInfoDto(entity.getAuthor()))
                .parentPost(this.entityToBasicPostInfoDto(entity.getParentPost()))
                .rating(entity.getVotes() == null
                        ? 0
                        : entity.getVotes().stream().map(vote -> vote.getRating() ? 1 : -1).reduce(0, Integer::sum))
                .tags(entity.getTags().stream().map(tag->tag.getText()).collect(Collectors.toList()))
                .build();
    }

    public PostEntity addPostDtoToEntity(AddPostDto dto, UserEntity postAuthor, PostEntity parentPost, Set<TagEntity> tags) {
        return PostEntity.builder()
                .title(dto.getTitle())
                .tags(tags)
                .description(dto.getDescription())
                .postPhotoName(dto.getPostPhoto() == null ? null : dto.getPostPhoto().getOriginalFilename())
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
