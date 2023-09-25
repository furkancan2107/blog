package com.rf.blogapp.controller;

import com.rf.blogapp.entity.User;
import com.rf.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;
    // kullanıcı oluştur
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody User user){
        return userService.createUser(user);
    }
    // kullanıcı giriş
    // şifremi unuttum
}
