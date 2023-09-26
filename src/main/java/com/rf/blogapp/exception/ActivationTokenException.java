package com.rf.blogapp.exception;

import org.jboss.logging.Messages;

public class ActivationTokenException extends RuntimeException{
    public ActivationTokenException(){
        super("Aktivasyon kodu doğrulanamadi");
    }

}
