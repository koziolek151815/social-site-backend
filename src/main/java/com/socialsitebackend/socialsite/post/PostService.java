package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.PostEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.entities.Vote;
import com.socialsitebackend.socialsite.exceptions.PostNotFoundException;
import com.socialsitebackend.socialsite.post.dto.AddPostDto;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.socialsitebackend.socialsite.post.dto.PostVoteDto;
import com.socialsitebackend.socialsite.user.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
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

    public PostEntity getParentPostEntityById(Long postId){
        if(postId == null) {return null;}
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }


    public PostEntity getPostEntityById(Long postId){
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Transactional
    public PostResponseDto createPost(AddPostDto dto, Long parentPostId) throws IOException{
        UserEntity user = userService.getCurrentUser();
        PostEntity parentPostEntity = getParentPostEntityById(parentPostId);

        uploadPostPhotoIfExists(dto.getPostPhoto());

        PostEntity newPostEntity = postRepository.save(postFactory.addPostDtoToEntity(dto, user, parentPostEntity));
        updateParentPost(parentPostEntity,newPostEntity);

        return postFactory.entityToResponseDto(newPostEntity);
    }

    public Page<PostResponseDto> getFrontPage(Pageable pageable) {
        List<PostResponseDto> list = postRepository.findAllByParentPostNull(pageable)
                .stream()
                .map(postFactory::entityToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(list);
    }

    public Page<PostResponseDto> getPostReplies(Long postId, Pageable pageable) {
        PostEntity parentPost = getPostEntityById(postId);
        List<PostResponseDto> list = postRepository.findAllByParentPostEquals(parentPost, pageable)
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

        Vote voteEntity = Vote.builder()
                .user(user)
                .post(post)
                .rating(vote.getVote())
                .build();

        List<Vote> voteList = post.getVotes();

        Optional<Vote> voteOptional = voteList
                .stream()
                .filter(x -> x.getUser().getId().equals(user.getId())).findFirst();

        if (voteOptional.isPresent()) {
            Vote oldVote = voteOptional.get();
            if (oldVote.getRating().equals(vote.getVote())) {
                voteList.remove(oldVote);
                oldVote.setPost(null);
                oldVote.setUser(null);
            } else {
                oldVote.setRating(vote.getVote());
            }
        } else
            voteList.add(voteEntity);

        return postFactory.entityToResponseDto(postRepository.save(post));
    }


    private void updateParentPost(PostEntity parent, PostEntity subPost){
        if(parent == null) return;
        parent.getSubPosts().add(subPost);
    }


    private void uploadPostPhotoIfExists(MultipartFile file) throws IOException {
        if (file == null) {
            return;
        }
        String originalName = file.getOriginalFilename();

        Path currentDir = Paths.get(".");
        String imageDirectory = currentDir.toAbsolutePath() + "/photos/";
        makeDirectoryIfNotExist(imageDirectory);

        Path fileNamePath = Paths.get(imageDirectory, originalName);
        Files.write(fileNamePath, file.getBytes());
    }

    private void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
      
    public int getUserVote(Long postId, Long userId) {
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        Optional<Vote> vote = post.getVotes()
                .stream()
                .filter(x -> x.getUser().getId() == userId)
                .findFirst();

        //If vote found return 1 for upvote or -1 for downvote
        if(vote.isPresent())
            return vote.get().getRating()?1:-1;
        //Else return 0
        return 0;
    }

    public int getCurrentUserVote(Long postId) {
        return getUserVote(postId, userService.getCurrentUser().getId());

    }
}
