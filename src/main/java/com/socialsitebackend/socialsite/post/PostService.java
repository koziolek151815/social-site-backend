package com.socialsitebackend.socialsite.post;


import com.socialsitebackend.socialsite.entities.PostEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.user.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostFactory postFactory;

    private final UserService userService;

    public PostService(PostRepository postRepository, PostFactory postFactory, UserService userService) {
        this.postRepository = postRepository;
        this.postFactory = postFactory;
        this.userService = userService;
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postFactory::entityToResponseDto)
                .collect(toList());
    }


    public PostResponseDto getPost(Long postId) {
        return postFactory.entityToResponseDto(postRepository.findById(postId).get());
    }

    @Transactional
    public PostResponseDto createPost(AddPostDto dto) {
        UserEntity user = userService.getCurrentUser();
        PostEntity postEntity = postRepository.save(postFactory.addPostDtoToEntity(dto, user));
        return postFactory.entityToResponseDto(postEntity);
    }


    public Page<PostResponseDto> getPageable(Pageable pageable) {

        List<PostResponseDto> list = postRepository.findAll(pageable)
                .stream()
                .map(postFactory::entityToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(list);
    }
}
