package com.rf.blogapp.service;
import com.rf.blogapp.dto.AuthResponse;
import com.rf.blogapp.dto.LoginDto;
import com.rf.blogapp.dto.UserRequest;
import com.rf.blogapp.dto.UserDto;
import com.rf.blogapp.entity.Token;
import com.rf.blogapp.entity.User;
import com.rf.blogapp.exception.ActivationTokenException;
import com.rf.blogapp.exception.LoginActivationTokenException;
import com.rf.blogapp.exception.LoginException;
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
    private final TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackOn = MailException.class)
    public ResponseEntity<?> createUser(UserRequest user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1=user.toUser();
        user1.setActivationCode(UUID.randomUUID().toString());
        user1.setActive(true);
        userRepository.save(user1);
        mailService.sendActivationMessage(user1);
        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .id(user1.getId()).username(user.getUsername())
                .build();
        return ResponseEntity.ok(userDto);
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
    public Page<UserDto> getUsers(int page, int size){
Page<User> userPage=userRepository.findAll(PageRequest.of(page,size));
        return userPage.map(this::convertToUserResponse);
    }
    private UserDto convertToUserResponse(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
    public UserDto getUser(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
        return convertToUserResponse(user);
    }
    public AuthResponse login(LoginDto loginDto){
        User user=userRepository.findByEmail(loginDto.getEmail());
        if(user==null) throw new LoginException();
        if(!passwordEncoder.matches(loginDto.getPassword(),user.getPassword())) throw new LoginException();
        if(!user.isActive()) throw new LoginActivationTokenException();
        Token token=tokenService.createToken(user);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setUser(convertToUserResponse(user));
        authResponse.setToken(token);
        return authResponse;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existById(Long userId) {
        return userRepository.existsById(userId);
    }
    public User findByUser(Long id){
        return userRepository.findById(id).orElseThrow(()->new UserNotFoundException());
    }

    public void logout(String authorization) {
        tokenService.logout(authorization);
    }
}
