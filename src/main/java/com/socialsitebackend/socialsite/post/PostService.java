package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.PostEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.exceptions.PostNotFoundException;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.post.dto.PostVoteDto;
import com.socialsitebackend.socialsite.user.UserRepository;
import com.socialsitebackend.socialsite.user.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    private final PostFactory postFactory;

    private final UserService userService;


    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postFactory::entityToResponseDto)
                .collect(toList());
    }

    public PostResponseDto getPostById(Long postId) {
        return postFactory.entityToResponseDto(
                postRepository.findById(postId)
                        .orElseThrow(() -> new PostNotFoundException(postId))
        );
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

    @Transactional
    public PostResponseDto addVote(Long postId, PostVoteDto vote) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        UserEntity user = userService.getCurrentUser();

        Map<UserEntity, Boolean> votesMap = post.getVotes();

        if (votesMap.containsKey(user))
            if (votesMap.get(user).equals(vote.getVote()))
                votesMap.remove(user);
            else
                votesMap.put(user, vote.getVote());
        else
            votesMap.put(user, vote.getVote());


        return postFactory.entityToResponseDto(postRepository.save(post));
    }
}
