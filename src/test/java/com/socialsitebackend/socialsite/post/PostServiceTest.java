package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.user.UserService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private UserService userService;

    @Test
    public void post_can_be_created() {
        UserEntity user = UserEntity.builder()
                .username("test")
                .email("test@test.com")
                .id(1L)
                .userCreatedDate(LocalDateTime.now())
                .build();

        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        AddPostDto addPostDto = PostSampleGenerator.getSampleAddPostDto();
        PostResponseDto createdPost = postService.createPost(addPostDto);

        assertEquals(addPostDto.getTitle(), createdPost.getTitle());
        assertEquals(addPostDto.getDescription(), createdPost.getDescription());
        assertEquals(addPostDto.getPhotoUrl(), createdPost.getPhotoUrl());
    }

    @Test
    public void post_can_be_found_by_id() {
        UserEntity user = UserEntity.builder()
                .username("test")
                .email("test@test.com")
                .id(1L)
                .userCreatedDate(LocalDateTime.now())
                .build();

        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        AddPostDto addPostDto = PostSampleGenerator.getSampleAddPostDto();
        PostResponseDto createdPost = postService.createPost(addPostDto);
        PostResponseDto foundPost = postService.getPostById(createdPost.getPostId());

        assertEquals(addPostDto.getTitle(), foundPost.getTitle());
        assertEquals(addPostDto.getDescription(), foundPost.getDescription());
        assertEquals(addPostDto.getPhotoUrl(), foundPost.getPhotoUrl());
    }

    @Test
    public void get_page_should_be_valid() {
        UserEntity user = UserEntity.builder()
                .username("test")
                .email("test@test.com")
                .id(1L)
                .userCreatedDate(LocalDateTime.now())
                .build();

        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        List<AddPostDto> addPosts = Arrays.asList(
                PostSampleGenerator.getSampleAddPostDto(),
                PostSampleGenerator.getSampleAddPostDto(),
                PostSampleGenerator.getSampleAddPostDto()
        );

        for (AddPostDto prd :
                addPosts) {
            postService.createPost(prd);
        }

        Pageable pageRequest = PageRequest.of(0, 5);
        Page<PostResponseDto> resultPage = postService.getPageable(pageRequest);
        List<PostResponseDto> resultList = resultPage.getContent();

        assertEquals(3, resultList.size());

        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(addPosts.get(i).getTitle(), resultList.get(i).getTitle());
            assertEquals(addPosts.get(i).getDescription(), resultList.get(i).getDescription());
            assertEquals(addPosts.get(i).getPhotoUrl(), resultList.get(i).getPhotoUrl());
        }
    }

    @Test
    public void get_all_posts_should_return_valid_posts() {
        UserEntity user = UserEntity.builder()
                .username("test")
                .email("test@test.com")
                .id(1L)
                .userCreatedDate(LocalDateTime.now())
                .build();

        Mockito.when(userService.getCurrentUser()).thenReturn(user);

        List<AddPostDto> addPosts = Arrays.asList(
                PostSampleGenerator.getSampleAddPostDto(),
                PostSampleGenerator.getSampleAddPostDto(),
                PostSampleGenerator.getSampleAddPostDto()
        );

        for (AddPostDto prd :
                addPosts) {
            postService.createPost(prd);
        }

        List<PostResponseDto> resultList = postService.getAllPosts();

        for (int i = 0; i < resultList.size(); i++) {
            assertEquals(addPosts.get(i).getTitle(), resultList.get(i).getTitle());
            assertEquals(addPosts.get(i).getDescription(), resultList.get(i).getDescription());
            assertEquals(addPosts.get(i).getPhotoUrl(), resultList.get(i).getPhotoUrl());
        }
    }

}
