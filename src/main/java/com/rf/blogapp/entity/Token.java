package com.rf.blogapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    @Transient
    private String prefix="Bearer";
    @Id
    private String token;
    @JsonIgnore
    @ManyToOne
    private User user;

    public Token(String prefix,String token){
        this.prefix=prefix;
        this.token=token;
    }

}
