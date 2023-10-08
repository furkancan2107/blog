package com.rf.blogapp.service;

import com.rf.blogapp.entity.Token;
import com.rf.blogapp.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtTokenService implements TokenService {
    @Autowired
            private UserService userService;
    SecretKey key= Keys.hmacShaKeyFor("secret-must-be-at-least-32-chars".getBytes());
    public Token createToken(User user){
        String token=Jwts.builder().setSubject(Long.toString(user.getId())).signWith(key).compact();
        return new Token("Bearer",token);
    }
    public User verifyToken(String authorizationHeader){
        if (authorizationHeader==null) return  null;
        String token=authorizationHeader.split(" ")[1];
        JwtParser parser= Jwts.parserBuilder().setSigningKey(key).build();
       try {
           Jws<Claims> claims= parser.parseClaimsJws(token);
           long userId=Long.valueOf(claims.getBody().getSubject());
           System.out.println(userId);
           User user=userService.findByUser(userId);
           return user;
       }catch (JwtException e){
           e.printStackTrace();
       }
       return null;
    }

    @Override
    public void logout(String authorizationHeader) {

    }
}
