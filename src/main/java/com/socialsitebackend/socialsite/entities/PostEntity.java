package com.socialsitebackend.socialsite.entities;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
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

    @Column(name = "post_photo_name")
    private String postPhotoName;

    @Column(name = "post_created_date")
    private LocalDateTime postCreatedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity author;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> votes;

    @ManyToOne(fetch = FetchType.LAZY)
    private PostEntity parentPost;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<TagEntity> tags;

    @OneToMany(mappedBy = "parentPost")
    private Set<PostEntity> subPosts = new HashSet<>();

}
