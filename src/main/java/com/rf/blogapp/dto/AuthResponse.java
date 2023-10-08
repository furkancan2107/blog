package com.rf.blogapp.dto;

import com.rf.blogapp.entity.Token;
import com.rf.blogapp.entity.User;
import lombok.Data;

@Data
public class AuthResponse {
    private UserDto user;
    private Token token;
}
