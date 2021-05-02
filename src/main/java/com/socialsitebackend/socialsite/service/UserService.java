package com.socialsitebackend.socialsite.service;


import com.socialsitebackend.socialsite.dto.UserRegisterRequestDto;
import com.socialsitebackend.socialsite.factory.UserFactory;
import com.socialsitebackend.socialsite.model.Role;
import com.socialsitebackend.socialsite.model.User;
import com.socialsitebackend.socialsite.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final UserFactory factory;

    public UserService(RoleService roleService, UserRepository userRepository, UserFactory factory) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.factory = factory;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername());
    }

    public User save(UserRegisterRequestDto userRegisterDto) {

        User newUser = factory.registeDtorToEntity(userRegisterDto);

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        if(newUser.getEmail().split("@")[1].equals("admin.edu")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }

        newUser.setRoles(roleSet);
        return userRepository.save(newUser);
    }
}
