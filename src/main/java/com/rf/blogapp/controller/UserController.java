package com.rf.blogapp.controller;

import com.rf.blogapp.dto.UserRequest;
import com.rf.blogapp.entity.User;
import com.rf.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    // kullanıcı oluştur
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody UserRequest user){
        return userService.createUser(user);
    }
    // hesap aktifleştirme
    @PatchMapping("/{token}/active")
    public ResponseEntity<?> activateUser(@PathVariable String token){
        return userService.activateUser(token);
    }
    // kullanıcı giriş
    // şifremi unuttum
}
