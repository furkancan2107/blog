package com.rf.blogapp.exception;

public class LoginActivationTokenException extends RuntimeException{
    public LoginActivationTokenException(){
        super("Lütfen giriş yapmak için mail adresine gelen linke tıkla");
    }
}
