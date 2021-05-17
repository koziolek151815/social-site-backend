package com.socialsitebackend.socialsite.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Builder
@Data
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

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "votes")
    @MapKeyJoinColumn(name = "user_id")
    @JoinColumn(name = "post_id")
    @Column(name = "vote_type")
    Map<UserEntity, Boolean> votes;

}
