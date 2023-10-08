package com.rf.blogapp.exception;

public class LoginException extends RuntimeException{
    public LoginException(){
        super("E mail veya Şifre yanlış");
    }
}
