package com.socialsitebackend.socialsite.post;


import com.socialsitebackend.socialsite.exceptions.PostNotFoundException;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;

import com.socialsitebackend.socialsite.post.dto.PostVoteDto;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<PostResponseDto> createPost(@RequestParam(required = false) Long parentPostId, @ModelAttribute AddPostDto dto) {
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(postService.createPost(dto, parentPostId));
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    ResponseEntity<List<PostResponseDto>> getAllPosts() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postService.getAllPosts());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    ResponseEntity<?> getPostById(@RequestParam Long postId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postService.getPostById(postId));
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @RequestMapping(value = "/getFrontPage", method = RequestMethod.GET)
    ResponseEntity<Page<PostResponseDto>> getFrontPage(
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "postCreatedDate", direction = Sort.Direction.DESC)
            }) Pageable pageable) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postService.getFrontPage(pageable));
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @RequestMapping(value = "/getPostReplies", method = RequestMethod.GET)
    ResponseEntity<Page<PostResponseDto>> getPostReplies(@RequestParam Long postId,
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "postCreatedDate", direction = Sort.Direction.ASC)
            }) Pageable pageable) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postService.getPostReplies(postId, pageable));
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

    }

    @RequestMapping(value = "/getCurrentUserVote", method = RequestMethod.GET)
    ResponseEntity<?> getCurrentUserVote(@RequestParam Long postId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postService.getCurrentUserVote(postId));
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    ResponseEntity<?> votePostById(@RequestParam Long postId, @Valid @RequestBody PostVoteDto postVoteDto) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postService.addVote(postId, postVoteDto));
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @RequestMapping(value= "/getPhoto", method = RequestMethod.GET)
    ResponseEntity<byte[]> getPhoto(@RequestParam Long postId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(postService.getPhotobytesByPostId(postId));
        } catch (PostNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
