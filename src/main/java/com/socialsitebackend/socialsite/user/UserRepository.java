package com.socialsitebackend.socialsite.user;

import com.socialsitebackend.socialsite.entities.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmailAndUserActiveTrue(String email);

    Optional<UserEntity> findByEmail(String email);

    void deleteUserEntityByUsername(String test);

    Boolean existsByEmailAndUserActiveTrue(String email);

    Boolean existsByEmail(String email);
}
