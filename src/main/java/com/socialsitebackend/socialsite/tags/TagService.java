package com.socialsitebackend.socialsite.tags;

import com.socialsitebackend.socialsite.entities.TagEntity;
import com.socialsitebackend.socialsite.exceptions.TagNotFoundException;
import com.socialsitebackend.socialsite.post.PostFactory;
import com.socialsitebackend.socialsite.post.PostRepository;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
import com.sun.xml.bind.v2.runtime.reflect.Lister;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    private final PostRepository postRepository;

    private final PostFactory postFactory;

    @Transactional
    public TagEntity createOrGetTag(String tag)
    {
        Optional<TagEntity> tagEntity = tagRepository.getByTagName(tag);

        //If tag exists return it
        if(tagEntity.isPresent()) return tagEntity.get();
        //Else create new TagEntity
        TagEntity newTagEntity = TagEntity.builder().tagName(tag).build();
        return tagRepository.save(newTagEntity);
    }

    @Transactional
    public Set<TagEntity> createOrGetTags(List<String> tags)
    {
        if(tags == null) return new HashSet<TagEntity>();
        return tags.stream().map(this::createOrGetTag).collect(Collectors.toSet());
    }

    public Page<PostResponseDto> getTagPage(Pageable pageable, String tagName) {
        Optional<TagEntity> tagEntity = tagRepository.getByTagName(tagName);

        //If tag doesn't exist return empty pageable
        if(!tagEntity.isPresent()) new PageImpl<>(new ArrayList<PostResponseDto>());
        //Else take all posts that aren't comments and return a pageable containing them
        List<PostResponseDto> list = postRepository.findAllByTagsContainingAndParentPostNull(tagEntity.get(),pageable)
                .stream()
                .map(postFactory::entityToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(list);
    }
}
