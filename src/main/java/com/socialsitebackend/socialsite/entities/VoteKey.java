package com.socialsitebackend.socialsite.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.io.Serializable;


@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "post_id")
    private Long postId;
}
