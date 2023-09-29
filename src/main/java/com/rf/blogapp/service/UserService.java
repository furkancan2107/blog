package com.rf.blogapp.service;
import com.rf.blogapp.dto.UserRequest;
import com.rf.blogapp.dto.UserResponse;
import com.rf.blogapp.entity.User;
import com.rf.blogapp.exception.ActivationTokenException;
import com.rf.blogapp.exception.UserNotFoundException;
import com.rf.blogapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MailService mailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackOn = MailException.class)
    public ResponseEntity<?> createUser(UserRequest user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
    public Page<UserResponse> getUsers(int page,int size){
Page<User> userPage=userRepository.findAll(PageRequest.of(page,size));
        return userPage.map(this::convertToUserResponse);
    }
    private UserResponse convertToUserResponse(User user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
    public UserResponse getUser(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
        return convertToUserResponse(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
