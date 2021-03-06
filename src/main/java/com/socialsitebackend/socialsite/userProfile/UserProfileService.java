package com.socialsitebackend.socialsite.userProfile;

import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.exceptions.UserNotFoundException;
import com.socialsitebackend.socialsite.post.PostRepository;
import com.socialsitebackend.socialsite.post.PostService;
import com.socialsitebackend.socialsite.post.VoteRepository;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.user.UserFactory;
import com.socialsitebackend.socialsite.user.UserRepository;
import com.socialsitebackend.socialsite.user.UserService;
import com.socialsitebackend.socialsite.userProfile.dto.UserEditProfileDto;
import com.socialsitebackend.socialsite.userProfile.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserProfileService {
    private final UserService userService;
    private final UserFactory userFactory;
    private final UserRepository userRepository;
    private final PostService postService;

    public UserProfileDto getCurrentUserProfile() {
        UserEntity currentUser = userService.getCurrentUser();
        return userFactory.entityToUserProfileDto(currentUser);
    }

    public UserProfileDto getUserProfile(Long userId) {
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return userFactory.entityToUserProfileDto(user);
    }
    public Page<PostResponseDto> getPostsCreatedByUser(Pageable pageable, Long userId) {
        UserEntity author = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return postService.getPostsCreatedByUser(pageable, author);
    }
    public Page<PostResponseDto> getCommentsCreatedByUser(Pageable pageable, Long userId) {
        UserEntity author = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return postService.getCommentsCreatedByUser(pageable, author);
    }

    public Page<PostResponseDto> getPostsAndCommentsVotedByUser(Pageable pageable, Long userId) {
        UserEntity author = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return postService.getPostsAndCommentsVotedByUser(pageable, author);
    }

    public void updateProfile(UserEditProfileDto dto) {
        UserEntity user = userService.getCurrentUser();

        user.setGender(dto.getGender());
        user.setProfileDescription(dto.getProfileDescription());
        user.setUsername(dto.getUsername());

        userRepository.save(user);
    }
}
