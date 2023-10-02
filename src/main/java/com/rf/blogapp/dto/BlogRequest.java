package com.rf.blogapp.dto;

import com.rf.blogapp.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogRequest {

    @NotBlank(message = "Boş değer olamaz")
    @Size(min = 2)
    private String title;
    @NotBlank(message = "Boş değer olamaz")
    @Size(min = 2)
    private String content;



}
