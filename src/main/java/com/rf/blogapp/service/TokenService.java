package com.rf.blogapp.service;

import com.rf.blogapp.entity.Token;
import com.rf.blogapp.entity.User;

public interface TokenService {
    public Token createToken(User user);
    public User verifyToken(String authorizationHeader);
    public void logout(String authorizationHeader);
}
