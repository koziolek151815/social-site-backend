package com.socialsitebackend.socialsite;

import com.socialsitebackend.socialsite.entities.RoleEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.role.RoleRepository;
import com.socialsitebackend.socialsite.role.RoleService;
import com.socialsitebackend.socialsite.user.UserFactory;
import com.socialsitebackend.socialsite.user.UserRepository;
import com.socialsitebackend.socialsite.user.UserService;
import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        userRepository.deleteUserEntityByUsername("test");


        RoleEntity adminRole = roleService.getAdminRole();
        RoleEntity userRole = roleService.getUserRole();


        Set<RoleEntity> roleEntitySet = new HashSet<>();
        roleEntitySet.add(adminRole);
        roleEntitySet.add(userRole);

        UserEntity user = UserEntity.builder()
                .id(1L)
                .username("test")
                .email("test@test.com")
                .avatarUrl("test")
                .gender("test")
                .password(bcryptEncoder.encode("test"))
                .build();

        user.setRoles(roleEntitySet);

        userRepository.save(user);
    }


}
