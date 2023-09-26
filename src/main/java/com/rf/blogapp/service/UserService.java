package com.rf.blogapp.service;

import com.rf.blogapp.configuration.WebConfiguration;
import com.rf.blogapp.dto.UserRequest;
import com.rf.blogapp.dto.UserResponse;
import com.rf.blogapp.entity.User;
import com.rf.blogapp.exception.ActivationTokenException;
import com.rf.blogapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final WebConfiguration configuration;
    private final MailService mailService;

    @Transactional(rollbackOn = MailException.class)
    public ResponseEntity<?> createUser(UserRequest user){
        user.setPassword(configuration.getPasswordEncoder().encode(user.getPassword()));
        User user1=user.toUser();
        user1.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user1);
        mailService.sendActivationMessage(user1);
        UserResponse userResponse=UserResponse.builder()
                .email(user.getEmail())
                .id(user1.getId()).username(user.getUsername())
                .build();
        return ResponseEntity.ok(userResponse);
    }
    public ResponseEntity<?> activateUser(String token){
        User user=userRepository.findByActivationCode(token);
        if(user==null){
            throw new ActivationTokenException();
        }
        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);
        return ResponseEntity.ok("Hesap Aktif oldu");
    }


}
