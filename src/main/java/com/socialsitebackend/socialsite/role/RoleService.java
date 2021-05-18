package com.socialsitebackend.socialsite.role;

import com.socialsitebackend.socialsite.entities.RoleEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service(value = "roleService")
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity getUserRole(){
        return roleRepository.findRoleByName("user").orElse(RoleEntity.builder()
                .id(1)
                .name("user")
                .description("test")
                .build());
    }

    public RoleEntity getAdminRole(){
        return roleRepository.findRoleByName("admin").orElse(RoleEntity.builder()
                .id(2)
                .name("admin")
                .description("test")
                .build());

    }
}
