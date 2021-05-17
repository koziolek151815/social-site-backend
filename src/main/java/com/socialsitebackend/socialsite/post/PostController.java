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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<PostResponseDto> createPost(@RequestBody AddPostDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.createPost(dto));
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getAllPosts());
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

    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    ResponseEntity<Page<PostResponseDto>> getPageable(
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "postCreatedDate", direction = Sort.Direction.DESC)
            }) Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.getPageable(pageable));
    }

    @RequestMapping(value = "/getById", method = RequestMethod.POST)
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
}
