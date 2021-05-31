package com.socialsitebackend.socialsite.post;

import com.socialsitebackend.socialsite.entities.PostEntity;

import com.socialsitebackend.socialsite.entities.TagEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findAllByParentPostEquals(PostEntity parentPost, Pageable pageable);

    List<PostEntity> findAllByParentPostNull(Pageable pageable);

    List<PostEntity> findAllByVotesContaining(UserEntity user);

    List<PostEntity> findAllByTagsContainingAndParentPostNull(TagEntity tag, Pageable pageable);
}
