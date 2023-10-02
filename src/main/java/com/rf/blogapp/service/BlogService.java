package com.rf.blogapp.service;

import com.rf.blogapp.dto.BlogDto;
import com.rf.blogapp.dto.BlogRequest;
import com.rf.blogapp.dto.DtoConverter;
import com.rf.blogapp.entity.Blog;
import com.rf.blogapp.exception.BlogNotFoundException;
import com.rf.blogapp.exception.UserNotFoundException;
import com.rf.blogapp.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private DtoConverter dtoConverter;
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
        if(!blogRepository.existsById(id)){
           throw new BlogNotFoundException(id);
        }
        return ResponseEntity.ok(id+ "numaralı blog silindi");
    }

    public ResponseEntity<?> updateBlog(BlogRequest blogRequest,Long blogId) {
        Blog blog=blogRepository.findById(blogId).orElseThrow(()->new BlogNotFoundException(blogId));
        blog.setContent(blogRequest.getContent());
        blog.setTitle(blogRequest.getTitle());
        blogRepository.save(blog);
        return ResponseEntity.ok(blogId+" numaralı blog güncellendi");
    }

    public List<BlogDto> getUserBlog(Long userId) {
        List<Blog> blogs=new ArrayList<>();
        for (Blog blog : blogRepository.findAll()){
            if(blog.getUser().getId().equals(userId)){
                blogs.add(blog);
            }
        }
        return blogs.stream().map(x->dtoConverter.convertDto(x)).collect(Collectors.toList());
    }

    public Page<BlogDto> getBlogList(int page,int size) {
        Page<Blog> blogPage=blogRepository.findAll(PageRequest.of(page,size));
        return blogPage.map(dtoConverter::convertDto);

    }

}
