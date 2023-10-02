package com.rf.blogapp.controller;

import com.rf.blogapp.dto.BlogDto;
import com.rf.blogapp.dto.CommentRequest;
import com.rf.blogapp.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    // yorum ekle
    @PostMapping("/add/{user}/{blog}")
    public ResponseEntity<?> addComment(@RequestBody CommentRequest comment, @PathVariable UserDto user, @PathVariable BlogDto blog){
        return null;
    }
    // yorum sil
}
