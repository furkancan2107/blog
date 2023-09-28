package com.rf.blogapp.error;

import com.rf.blogapp.exception.ActivationTokenException;
import com.rf.blogapp.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validationException(MethodArgumentNotValidException ex){
        ApiError apiError=new ApiError();
        Map<String,String> validationErrors=new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()){
            validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        apiError=ApiError.builder().
        status(400).timestamp(apiError.getTimestamp()).errors(validationErrors).message("error").path("validationErrors").build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
}
    @ExceptionHandler(ActivationTokenException.class)
    public ResponseEntity<ApiError> activationException(ActivationTokenException ex,HttpServletRequest request){
        ApiError apiError=new ApiError();
        apiError=ApiError.builder().path(request.getRequestURI()).timestamp(apiError.getTimestamp()).status(400).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiError> userNotFoundException(UserNotFoundException ex, HttpServletRequest request){
        ApiError apiError=new ApiError();
        apiError=ApiError.builder().path(request.getRequestURI()).timestamp(apiError.getTimestamp()).status(404).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }


}
