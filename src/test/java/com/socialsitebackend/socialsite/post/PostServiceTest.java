package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.user.UserService;

import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;
import com.socialsitebackend.socialsite.user.dto.UserRegisterResponseDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private UserService userService;

    @Test
    public void post_can_be_created() throws IOException {
        UserEntity user = UserEntity.builder()
                .username("test")
                .email("test@test.com")
                .id(1L)
                .userCreatedDate(Instant.now())
                .build();

        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        AddPostDto addPostDto = PostSampleGenerator.getSampleAddPostDto();
        PostResponseDto createdPost = postService.createPost(addPostDto, null);

        assertEquals(addPostDto.getTitle(), createdPost.getTitle());
        assertEquals(addPostDto.getDescription(), createdPost.getDescription());
    }

    @Test
    public void post_can_be_found_by_id() throws IOException{
        UserEntity user = UserEntity.builder()
                .username("test")
                .email("test@test.com")
                .id(1L)
                .userCreatedDate(Instant.now())
                .build();

        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        AddPostDto addPostDto = PostSampleGenerator.getSampleAddPostDto();
        PostResponseDto createdPost = postService.createPost(addPostDto, null);
        PostResponseDto foundPost = postService.getPostById(createdPost.getPostId());

        assertEquals(addPostDto.getTitle(), foundPost.getTitle());
        assertEquals(addPostDto.getDescription(), foundPost.getDescription());
    }

}
