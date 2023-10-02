package com.rf.blogapp.controller;

import com.rf.blogapp.dto.BlogDto;
import com.rf.blogapp.dto.BlogRequest;
import com.rf.blogapp.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/blog")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;
    // yeni blog
    @PostMapping("/createBlog/{userId}")
    public ResponseEntity<?> createBlog(@Valid @RequestBody BlogRequest blogRequest, @PathVariable Long userId){
        return blogService.createBlog(blogRequest,userId);
    }
    // blog sil
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Long id){
        return blogService.deleteBlog(id);
    }
    // blog güncelleme
    @PutMapping("/update/{blogId}")
    public ResponseEntity<?> updateBlog(@RequestBody BlogRequest blogRequest,@PathVariable Long blogId){
        return blogService.updateBlog(blogRequest,blogId);
    }
    // kullanıcıya ait blogları getir
    @GetMapping("/getBlogs/{userId}")
    public List<BlogDto> getUserBlog(@PathVariable Long userId){
        return blogService.getUserBlog(userId);
    }
    // tüm blogları Page formatında getir
    @GetMapping("/getBlogs")
    public Page<BlogDto> getBlogList(){
        return blogService.getBlogList();
    }
}
