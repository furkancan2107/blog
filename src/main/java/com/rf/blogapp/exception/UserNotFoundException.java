package com.rf.blogapp.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(){
        super("Kullanici Bulunamadi");
    }
}
