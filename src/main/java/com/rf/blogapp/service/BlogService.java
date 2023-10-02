package com.rf.blogapp.service;

import com.rf.blogapp.dto.BlogDto;
import com.rf.blogapp.dto.BlogRequest;
import com.rf.blogapp.entity.Blog;
import com.rf.blogapp.entity.User;
import com.rf.blogapp.exception.UserNotFoundException;
import com.rf.blogapp.repository.BlogRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserService userService;
    public ResponseEntity<?> createBlog( BlogRequest blogRequest, Long userId) {
       if(!userService.existById(userId)){
           throw new UserNotFoundException();
       }
       Blog blog=Blog.builder().
        user(userService.findByUser(userId)).title(blogRequest.getTitle())
                       .content(blogRequest.getContent()).
               build();
       blogRepository.save(blog);
       return ResponseEntity.ok("Yeni blog eklendi");
    }

    public ResponseEntity<?> deleteBlog(Long id) {
        return null;
    }

    public ResponseEntity<?> updateBlog(BlogRequest blogRequest, Long blogId) {
        return null;
    }

    public List<BlogDto> getUserBlog(Long userId) {
        return null;
    }

    public Page<BlogDto> getBlogList() {
        return null;
    }
}
