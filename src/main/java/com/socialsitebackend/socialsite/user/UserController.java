package com.socialsitebackend.socialsite.user;

import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.config.security.TokenProvider;
import com.socialsitebackend.socialsite.exceptions.EmailAlreadyTakenException;
import com.socialsitebackend.socialsite.user.dto.AuthToken;
import com.socialsitebackend.socialsite.user.dto.UserLoginRequestDto;
import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;

import com.socialsitebackend.socialsite.user.dto.UserRegisterResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final UserService userService;


    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody UserLoginRequestDto loginUser) throws AuthenticationException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getEmail(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);

            return ResponseEntity.ok(new AuthToken(token));

        } catch (AuthenticationException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserRegisterResponseDto> saveUser(@RequestBody UserRegisterRequestDto user) {
        try{
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userService.save(user));
        } catch (EmailAlreadyTakenException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/adminping", method = RequestMethod.GET)
    public String adminPing() {
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value = "/userping", method = RequestMethod.GET)
    public String userPing() {
        return "Any User Can Read This";
    }

    @RequestMapping(value = "/usernew", method = RequestMethod.GET)
    public String userTest() {
        return "Any User Can Read This";
    }
}
