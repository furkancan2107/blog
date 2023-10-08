package com.rf.blogapp.service;

import com.rf.blogapp.entity.Token;
import com.rf.blogapp.entity.User;
import com.rf.blogapp.exception.DeleteTokenException;
import com.rf.blogapp.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
@Primary
public class OurTokenService implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;
    @Override
    public Token createToken(User user) {
        String value= UUID.randomUUID().toString();
        Token token=new Token();
        token.setToken(value);
        token.setPrefix("Bearer");
        token.setUser(user);
        tokenRepository.save(token);
        return token;
    }

    @Override
    public User verifyToken(String authorizationHeader) {
        Token tokenDb=getToken(authorizationHeader).orElseThrow(()-> new DeleteTokenException());
        return tokenDb.getUser();
    }

    @Override
    public void logout(String authorizationHeader) {
        Token tokenDb=getToken(authorizationHeader).orElseThrow(()-> new DeleteTokenException());
        tokenRepository.delete(tokenDb);
    }
    private Optional<Token> getToken(String authorization){
        if (authorization==null) return  Optional.empty();
        String token=authorization.split(" ")[1];
        return tokenRepository.findById(token);
    }
}
