package com.socialsitebackend.socialsite.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PostEntity {

    @Id
    @GeneratedValue
    private Long postId;

    private String title;
    private String description;
    private String photoUrl;
    private LocalDateTime postCreatedDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity postAuthor;

}
