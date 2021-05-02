package com.socialsitebackend.socialsite.role;


import com.socialsitebackend.socialsite.entities.Role;
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
