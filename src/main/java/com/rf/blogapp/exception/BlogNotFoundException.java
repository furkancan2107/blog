package com.rf.blogapp.exception;

public class BlogNotFoundException extends RuntimeException {
    public BlogNotFoundException(Long id){
        super(id+" numaralı Blog bulunamadi");
    }
}
