package com.socialsitebackend.socialsite.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "tags")
public class TagEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String tagName;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "tags")
    Set<PostEntity> taggedPosts;
}
