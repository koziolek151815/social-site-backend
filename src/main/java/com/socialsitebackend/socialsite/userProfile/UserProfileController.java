package com.socialsitebackend.socialsite.userProfile;


import com.socialsitebackend.socialsite.exceptions.PostNotFoundException;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.userProfile.dto.UserEditProfileDto;
import com.socialsitebackend.socialsite.userProfile.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/profile")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @RequestMapping(value = "/currentUser", method = RequestMethod.GET)
    public ResponseEntity<UserProfileDto> getCurrentUserProfile() {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userProfileService.getCurrentUserProfile());
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<UserProfileDto> getUserProfile(@RequestParam Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userProfileService.getUserProfile(userId));
        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @RequestMapping(value = "/user/posts", method = RequestMethod.GET)
    ResponseEntity<Page<PostResponseDto>> getPostsCreatedByUser(
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "postCreatedDate", direction = Sort.Direction.DESC)
            }) Pageable pageable, @RequestParam Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userProfileService.getPostsCreatedByUser(pageable, userId));
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

    @RequestMapping(value = "/user/comments", method = RequestMethod.GET)
    ResponseEntity<Page<PostResponseDto>> getCommentsCreatedByUser(
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "postCreatedDate", direction = Sort.Direction.DESC)
            }) Pageable pageable, @RequestParam Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userProfileService.getCommentsCreatedByUser(pageable, userId));
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

    @RequestMapping(value = "/user/votes", method = RequestMethod.GET)
    ResponseEntity<Page<PostResponseDto>> getPostsAndCommentsVotedByUser(
            @PageableDefault()
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "postCreatedDate", direction = Sort.Direction.DESC)
            }) Pageable pageable, @RequestParam Long userId) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(userProfileService.getPostsAndCommentsVotedByUser(pageable, userId));
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

    @RequestMapping(value = "/updateProfile", method = RequestMethod.PUT)
    ResponseEntity<Void> updateProfile(@RequestBody UserEditProfileDto dto) {
        try {
            userProfileService.updateProfile(dto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
