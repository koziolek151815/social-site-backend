package com.socialsitebackend.socialsite.post;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
public class PostControllerTest {

    @MockBean
    PostService postService;

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void add_post_should_be_unauthorized_without_user() throws Exception {
        AddPostDto addPostDto = PostSampleGenerator.getSampleAddPostDto();

        mockMvc.perform(post("/posts")
                .content(mapper.writeValueAsString(addPostDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void get_single_post_should_be_unauthorized_without_user() throws Exception {
        mockMvc.perform(get("/posts/getById").param("postId", "12345"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void get_single_post_should_return_post() throws Exception {

        PostResponseDto postResponseDto = PostSampleGenerator.getRandomPostResponseDto();

        Mockito.when(postService.getPostById(postResponseDto.getPostId())).thenReturn(postResponseDto);

        mockMvc.perform(get("/posts/getById").param("postId", postResponseDto.getPostId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.postId").value(postResponseDto.getPostId()))
                .andExpect(jsonPath("$.title").value(postResponseDto.getTitle()))
                .andExpect(jsonPath("$.description").value(postResponseDto.getDescription()));
    }

    @Test
    public void get_all_posts_should_be_unauthorized_without_user() throws Exception {
        mockMvc.perform(get("/posts/getAll"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void get_all_posts_should_return_posts() throws Exception {

        PostResponseDto[] postResponseDtos = new PostResponseDto[]{
                PostSampleGenerator.getRandomPostResponseDto(),
                PostSampleGenerator.getRandomPostResponseDto(),
                PostSampleGenerator.getRandomPostResponseDto()
        };

        List<PostResponseDto> postResponseDtoList = Arrays.asList(postResponseDtos);

        Mockito.when(postService.getAllPosts()).thenReturn(postResponseDtoList);

        mockMvc.perform(get("/posts/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].postId")
                        .value(Matchers.containsInRelativeOrder(postResponseDtoList.stream().map(PostResponseDto::getPostId).toArray())))
                .andExpect(jsonPath("$[*].title")
                        .value(Matchers.containsInRelativeOrder(postResponseDtoList.stream().map(PostResponseDto::getTitle).toArray())))
                .andExpect(jsonPath("$[*].description")
                        .value(Matchers.containsInRelativeOrder(postResponseDtoList.stream().map(PostResponseDto::getDescription).toArray())));
    }

    @Test
    public void get_pageable_should_be_unauthorized_without_user() throws Exception {
        mockMvc.perform(get("/posts/getPage"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    public void get_pageable_should_return_pageable() throws Exception {

        Page page = Mockito.mock(Page.class);

        Mockito.when(postService.getFrontPage(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/posts/getFrontPage"))
                .andExpect(status().isOk());
    }
}
