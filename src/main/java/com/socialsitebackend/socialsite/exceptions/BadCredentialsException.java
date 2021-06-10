package com.socialsitebackend.socialsite.exceptions;

public class BadCredentialsException extends RuntimeException{
    public  BadCredentialsException(String providedPassword) {
        super("Provided password: " + providedPassword + " is not valid");
    }
}
