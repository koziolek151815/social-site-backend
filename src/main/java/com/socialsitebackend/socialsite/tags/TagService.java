package com.socialsitebackend.socialsite.tags;

import com.socialsitebackend.socialsite.entities.TagEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public TagEntity createOrGetTag(String tag)
    {
        Optional<TagEntity> tagEntity = tagRepository.getByText(tag);

        if(tagEntity.isPresent()) return tagEntity.get();

        TagEntity newTagEntity = TagEntity.builder().text(tag).build();

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
}
