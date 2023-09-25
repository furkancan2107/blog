package com.rf.blogapp.service;

import com.rf.blogapp.configuration.WebConfiguration;
import com.rf.blogapp.dto.UserResponse;
import com.rf.blogapp.entity.User;
import com.rf.blogapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WebConfiguration configuration;

    public ResponseEntity<?> createUser(User user){
        user.setPassword(configuration.getPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        UserResponse userResponse=UserResponse.builder()
                .email(user.getEmail())
                .id(user.getId()).username(user.getUsername())
                .build();
        return ResponseEntity.ok(userResponse);
    }


}
