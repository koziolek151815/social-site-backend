package com.socialsitebackend.socialsite.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super("User for id =" + userId + " not found!");

    }
}
