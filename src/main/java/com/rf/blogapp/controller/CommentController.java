package com.rf.blogapp.controller;

import com.rf.blogapp.dto.BlogDto;
import com.rf.blogapp.dto.CommentRequest;
import com.rf.blogapp.dto.UserDto;
import com.rf.blogapp.entity.Comment;
import com.rf.blogapp.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    // yorum ekle
    @PostMapping("/add/{userId}/{blogId}")
    public ResponseEntity<?> addComment(@Valid @RequestBody CommentRequest comment, @PathVariable Long userId, @PathVariable Long blogId){
        return commentService.addComment(comment,userId,blogId);
    }
    // yorum sil
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        return commentService.deleteComment(id);
    }
}
