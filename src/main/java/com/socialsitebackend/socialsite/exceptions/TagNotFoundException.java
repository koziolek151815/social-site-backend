package com.socialsitebackend.socialsite.exceptions;

public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(String tagName) {
        super("Tag for name =" + tagName + " not found!");

    }
}

