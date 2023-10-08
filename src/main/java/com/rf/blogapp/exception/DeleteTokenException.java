package com.rf.blogapp.exception;

public class DeleteTokenException extends RuntimeException{
    public DeleteTokenException(){
        super("Token bulunamadı");
    }
}
