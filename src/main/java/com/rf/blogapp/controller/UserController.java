package com.rf.blogapp.controller;

import com.rf.blogapp.dto.UserRequest;
import com.rf.blogapp.dto.UserDto;
import com.rf.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    // tüm kullanıcılari getir
    @GetMapping("/getUsers")
    public Page<UserDto> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return userService.getUsers(page,size);
    }
    @GetMapping("/getUser/{id}")
    public UserDto getUser(@PathVariable Long id){
        return userService.getUser(id);
    }
}
