package com.rf.blogapp.error;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiError> loginException(DisabledException ex, HttpServletRequest request){
        ApiError apiError=new ApiError();
               apiError= ApiError.builder().message(ex.getMessage()).
        timestamp(apiError.getTimestamp()).
                status(401).
                path(request.getRequestURI())
                .build();
        return  ResponseEntity.status(401).body(apiError);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialException(BadCredentialsException ex, HttpServletRequest request){
        ApiError apiError=new ApiError();
        apiError= ApiError.builder().message(ex.getMessage()).
                timestamp(apiError.getTimestamp()).
                status(401).
                path(request.getRequestURI())
                .build();
        return  ResponseEntity.status(401).body(apiError);
    }
}
