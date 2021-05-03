package com.socialsitebackend.socialsite.exceptions;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException(Long postId){
        super("Post for id =" + postId + " not found!");
    }
}
