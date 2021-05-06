package com.socialsitebackend.socialsite.user;

import com.socialsitebackend.socialsite.role.RoleService;
import com.socialsitebackend.socialsite.entities.RoleEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@RequiredArgsConstructor
@Service(value = "userService")
public class UserService implements UserDetailsService {

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final UserFactory factory;


    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(UserEntity user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(roleEntity -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getName()));
        });

        return authorities;
    }

    public UserEntity getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        return userRepository
                .findByEmail(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(principal.getUsername()));
    }

    public UserEntity save(UserRegisterRequestDto userRegisterDto) {
        UserEntity newUser = factory.registeDtorToEntity(userRegisterDto);

        RoleEntity role = roleService.findByName("USER");
        Set<RoleEntity> roleSet = new HashSet<>();
        roleSet.add(role);

        if (newUser.getEmail().split("@")[1].equals("admin.edu")) {
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
        }
        newUser.setRoles(roleSet);

        return userRepository.save(newUser);
    }
}
