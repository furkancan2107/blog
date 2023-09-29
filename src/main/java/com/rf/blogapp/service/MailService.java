package com.rf.blogapp.service;

import com.rf.blogapp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendActivationMessage(User user){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("noreply@myblog-app.com");
        message.setTo(user.getEmail());
        message.setSubject("Hesap Doğrulama");
        message.setText("http://localhost:5173/activation"+user.getActivationCode());
        javaMailSender.send(message);
    }
}
