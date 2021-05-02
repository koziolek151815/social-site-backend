package com.socialsitebackend.socialsite.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
 public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String email;


    private LocalDateTime userCreatedDate;
    private String avatarUrl;
    private String gender;
    private String profileDescription;


   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(name = "USER_ROLES",
           joinColumns = {
                   @JoinColumn(name = "USER_ID")
           },
           inverseJoinColumns = {
                   @JoinColumn(name = "ROLE_ID") })
   private Set<Role> roles;


    @PrePersist
    void createdAt() {
        this.userCreatedDate = LocalDateTime.now();
    }

}
