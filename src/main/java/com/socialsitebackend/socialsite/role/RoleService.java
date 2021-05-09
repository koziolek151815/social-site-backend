package com.socialsitebackend.socialsite.role;

import com.socialsitebackend.socialsite.entities.RoleEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service(value = "roleService")
public class RoleService {

    private final RoleRepository roleRepository;


    public RoleEntity findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
