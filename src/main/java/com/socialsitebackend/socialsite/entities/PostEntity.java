package com.socialsitebackend.socialsite.entities;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "posts")
public class PostEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "post_created_date")
    private LocalDateTime postCreatedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity author;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity parentPost;

    @OneToMany(mappedBy = "parentPost")
    private Set<PostEntity> subPosts = new HashSet<>();

}
