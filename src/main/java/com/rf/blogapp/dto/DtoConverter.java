package com.rf.blogapp.dto;

import com.rf.blogapp.dto.BlogDto;
import com.rf.blogapp.dto.UserDto;
import com.rf.blogapp.entity.Blog;
import com.rf.blogapp.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {
    public BlogDto convertDto(Blog x) {
        return BlogDto.builder().id(x.getId())
                .content(x.getContent())
                .title(x.getTitle())
                .user(userConvert(x.getUser()))
                .build();
    }
    public UserDto userConvert(User x){
        return UserDto.builder()
                .username(x.getUsername())
                .email(x.getEmail())
                .id(x.getId())
                .build();
    }
}
