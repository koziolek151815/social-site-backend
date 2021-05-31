package com.socialsitebackend.socialsite.tags;

import com.socialsitebackend.socialsite.entities.TagEntity;
import com.socialsitebackend.socialsite.exceptions.TagNotFoundException;
import com.socialsitebackend.socialsite.post.PostFactory;
import com.socialsitebackend.socialsite.post.PostRepository;
import com.socialsitebackend.socialsite.post.dto.PostResponseDto;
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

        if(tagEntity.isPresent()) return tagEntity.get();

        TagEntity newTagEntity = TagEntity.builder().tagName(tag).build();

        return tagRepository.save(newTagEntity);
    }

    @Transactional
    public Set<TagEntity> createOrGetTags(List<String> tags)
    {
        Set<TagEntity> set = new HashSet<TagEntity>();

        if(tags == null) return set;

        for (String tag:tags) {
            set.add(createOrGetTag(tag));
        }

        return set;
    }

    public TagEntity findTag(String tagName) {
        Optional<TagEntity> tagEntity = tagRepository.getByTagName(tagName);

        if(tagEntity.isPresent()) return tagEntity.get();

        else throw new TagNotFoundException(tagName);
    }

    public Page<PostResponseDto> getTagPage(Pageable pageable, String tagName) {
        TagEntity tag = findTag(tagName);

        List<PostResponseDto> list = postRepository.findAllByTagsContainingAndParentPostNull(tag,pageable)
                .stream()
                .map(postFactory::entityToResponseDto)
                .collect(Collectors.toList());

        return new PageImpl<>(list);


    }
}
