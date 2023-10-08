package com.rf.blogapp.controller;

import com.rf.blogapp.dto.AuthResponse;
import com.rf.blogapp.dto.LoginDto;
import com.rf.blogapp.dto.UserRequest;
import com.rf.blogapp.dto.UserDto;
import com.rf.blogapp.entity.Token;
import com.rf.blogapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.Cookie;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        AuthResponse authResponse=userService.login(loginDto);
        ResponseCookie cookie= ResponseCookie.from("furkancan-token",authResponse.getToken().getToken()).path("/").httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body(authResponse);
    }

    // logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization",required = false) String authorization,@CookieValue(name = "furkancan-token",required = false) String cookieValue){
        String prefix=authorization;
        if(cookieValue!=null){
            prefix="Prefix "+cookieValue;
        }
        userService.logout(prefix);
        ResponseCookie cookie= ResponseCookie.from("furkancan-token","").path("/").maxAge(0).httpOnly(true).build();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE,cookie.toString()).body("Başarı ile çıkış yapıldı");
    }
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
