package com.rf.blogapp.service;

import com.rf.blogapp.entity.User;
import com.rf.blogapp.exception.LoginActivationTokenException;
import com.rf.blogapp.exception.LoginException;
import com.rf.blogapp.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user=userService.findByEmail(email);
        if(user==null){
            throw new LoginException();
        }

        return user;
    }
}
