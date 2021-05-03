package com.socialsitebackend.socialsite.user;

import com.socialsitebackend.socialsite.entities.UserEntity;
import com.socialsitebackend.socialsite.config.security.TokenProvider;
import com.socialsitebackend.socialsite.exceptions.UserNotFoundException;
import com.socialsitebackend.socialsite.user.dto.AuthToken;
import com.socialsitebackend.socialsite.user.dto.UserLoginRequestDto;
import com.socialsitebackend.socialsite.user.dto.UserRegisterRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider jwtTokenUtil;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody UserLoginRequestDto loginUser) throws AuthenticationException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            return ResponseEntity.ok(new AuthToken(token));

        } catch (UserNotFoundException | AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }


    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public UserEntity saveUser(@RequestBody UserRegisterRequestDto user){
        return userService.save(user);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value="/adminping", method = RequestMethod.GET)
    public String adminPing(){
        return "Only Admins Can Read This";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping(value="/userping", method = RequestMethod.GET)
    public String userPing(){
        return "Any User Can Read This";
    }

    @RequestMapping(value="/usernew", method = RequestMethod.GET)
    public String userTest(){
        return "Any User Can Read This";
    }
}
