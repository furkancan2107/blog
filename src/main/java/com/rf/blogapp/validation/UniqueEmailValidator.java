package com.rf.blogapp.validation;

import com.rf.blogapp.entity.User;
import com.rf.blogapp.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
      if(userRepository.existsByEmail(s)){
          return  false;
      }
        return true;
    }
}
