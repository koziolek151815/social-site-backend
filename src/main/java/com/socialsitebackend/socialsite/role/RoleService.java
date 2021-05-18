package com.socialsitebackend.socialsite.role;

import com.socialsitebackend.socialsite.entities.RoleEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;


@RequiredArgsConstructor
@Service(value = "roleService")
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity getUserRole(){
        Optional<RoleEntity> role = roleRepository.findRoleByName("user");
        if(role.isPresent()) return role.get();

        RoleEntity newRole = RoleEntity.builder()
                .id(1)
                .name("user")
                .description("test")
                .build();
        roleRepository.save(newRole);
        return newRole;
    }

    public RoleEntity getAdminRole(){
        Optional<RoleEntity> role = roleRepository.findRoleByName("admin");
        if(role.isPresent()) return role.get();

        RoleEntity newRole = RoleEntity.builder()
                .id(1)
                .name("admin")
                .description("test")
                .build();
        roleRepository.save(newRole);
        return newRole;
    }
}
