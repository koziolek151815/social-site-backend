package com.socialsitebackend.socialsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

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


   @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinTable(name = "USER_ROLES",
           joinColumns = {
                   @JoinColumn(name = "USER_ID")
           },
           inverseJoinColumns = {
                   @JoinColumn(name = "ROLE_ID") })
   private Set<Role> roles;

}
