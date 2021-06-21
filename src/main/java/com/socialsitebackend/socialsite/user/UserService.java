package com.socialsitebackend.socialsite.user;

import com.socialsitebackend.socialsite.exceptions.BadCredentialsException;
import com.socialsitebackend.socialsite.exceptions.EmailAlreadyTakenException;
import com.socialsitebackend.socialsite.role.RoleService;
import com.socialsitebackend.socialsite.entities.RoleEntity;
import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.user.dto.ChangePasswordRequestDto;
import com.socialsitebackend.socialsite.user.dto.DeactivateUserRequestDto;
import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;

import com.socialsitebackend.socialsite.user.dto.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;


@RequiredArgsConstructor
@Service(value = "userService")
public class UserService implements UserDetailsService {

    private final RoleService roleService;

    private final UserRepository userRepository;

    private final UserFactory factory;

    private final BCryptPasswordEncoder bcryptEncoder;


    public UserDetails loadUserByUsername(String email) {
        UserEntity user = userRepository.findByEmailAndUserActiveTrue(email)
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

    @Transactional
    public UserEntity getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();

        return userRepository
                .findByEmailAndUserActiveTrue(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(principal.getUsername()));
    }

    @Transactional
    public UserRegisterResponseDto save(UserRegisterRequestDto userRegisterDto) {
        UserEntity newUser = factory.registeDtorToEntity(userRegisterDto);
        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new EmailAlreadyTakenException(userRegisterDto.getEmail());
        }


        RoleEntity role = roleService.getUserRole();
        Set<RoleEntity> roleSet = new HashSet<>();
        roleSet.add(role);

        if (newUser.getEmail().split("@")[1].equals("admin.edu")) {
            role = roleService.getAdminRole();
            roleSet.add(role);
        }
        newUser.setRoles(roleSet);

        return factory.entityToRegisterResponseDto(userRepository.save(newUser));
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        UserEntity currentUser = getCurrentUser();
        if (!bcryptEncoder.matches(changePasswordRequestDto.getOldPassword(), currentUser.getPassword())) {
            throw new BadCredentialsException(changePasswordRequestDto.getOldPassword());
        }
        currentUser.setPassword(bcryptEncoder.encode(changePasswordRequestDto.getNewPassword()));
        userRepository.save(currentUser);
    }

    @Transactional
    public void deactivateUser(DeactivateUserRequestDto deactivateUserRequestDto) {
        UserEntity currentUser = getCurrentUser();
        if (!bcryptEncoder.matches(deactivateUserRequestDto.getPassword(), currentUser.getPassword())) {
            throw new BadCredentialsException(deactivateUserRequestDto.getPassword());
        }
        currentUser.setUserActive(false);
        userRepository.save(currentUser);
    }
}
