package com.socialsitebackend.socialsite.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "images")
public class ImageEntity {

    @Id
    @GeneratedValue
    private long id;

    private String originalFilename;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] bytes;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PostEntity post;

    //@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //<- if we want to add profile pictures this has to be uncommented
    //private UserEntity user;
}
