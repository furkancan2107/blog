package com.rf.blogapp.service;


import com.rf.blogapp.dto.BlogDto;
import com.rf.blogapp.dto.CommentRequest;
import com.rf.blogapp.dto.UserDto;
import com.rf.blogapp.entity.Comment;
import com.rf.blogapp.exception.NotFoundException;
import com.rf.blogapp.exception.UserNotFoundException;
import com.rf.blogapp.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private CommentRepository commentRepository;
    private final UserService userService;
    private final  BlogService blogService;
    public ResponseEntity<?> addComment(CommentRequest commentRequest, Long userId,Long blogId){
        Comment comment=new Comment();
        comment.setComment(commentRequest.getContent());
        comment.setUser(userService.findByUser(userId));
        comment.setBlog(blogService.findById(blogId));
        commentRepository.save(comment);
        return ResponseEntity.ok("Yorum eklendi");
    }
    public ResponseEntity<?> deleteComment(Long id){
        Comment comment=commentRepository.findById(id).orElseThrow(()->new NotFoundException(id+" numaralı yorum bulunamadi"));
        return ResponseEntity.ok(id+" numarali yorum başarı ile silindi");
    }

}
