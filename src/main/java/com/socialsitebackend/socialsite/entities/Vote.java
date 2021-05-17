package com.socialsitebackend.socialsite.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;


@Entity(name = "votes")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    @EmbeddedId
    VoteKey id = new VoteKey();

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    UserEntity user;

    @ManyToOne
    @MapsId("postId")
    @JoinColumn(name = "post_id")
    PostEntity post;

    Boolean rating;
}
