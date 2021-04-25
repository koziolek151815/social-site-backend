package com.socialsitebackend.socialsite.service;


import com.socialsitebackend.socialsite.model.Role;
import com.socialsitebackend.socialsite.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service(value = "roleService")
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
