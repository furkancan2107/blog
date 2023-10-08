package com.rf.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class BlogAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);
	}

}
