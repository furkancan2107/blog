package com.rf.blogapp.dto;

import com.rf.blogapp.entity.User;
import com.rf.blogapp.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank
    @Size(min = 2,max = 256)
    private String username;

    @Email
    @NotBlank
    @UniqueEmail
    private String email;

    @Size(min = 8,max = 256)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{5,}$" ,message = "Lütfen en az bir büyük harf,bir küçük harf ve sayi kullanin")
    private String password;
    public User toUser(){
        User user=User.builder().
        email(email).username(username).password(password).build();
        return user;
    }
}
