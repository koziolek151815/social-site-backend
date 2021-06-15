package com.socialsitebackend.socialsite;

import com.socialsitebackend.socialsite.entities.RoleEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.role.RoleService;
import com.socialsitebackend.socialsite.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Component
@RequiredArgsConstructor
@Transactional
public class SampleDatabaseLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bcryptEncoder;

    public void run(String... strings) {


        UserEntity testUser = getTestUser();

        RoleEntity adminRole = roleService.getAdminRole();
        RoleEntity userRole = roleService.getUserRole();

        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(adminRole);
        roleEntitySet.add(userRole);

        testUser.setRoles(roleEntitySet);

        userRepository.save(testUser);
    }

    private UserEntity getTestUser(){
        return userRepository.findByEmail("test@test.com").orElse(
                UserEntity.builder()
                        .username("test")
                        .email("test@test.com")
                        .avatarUrl("test")
                        .gender("test")
                        .userActive(true)
                        .password(bcryptEncoder.encode("test"))
                        .build()
        );
    }


}
