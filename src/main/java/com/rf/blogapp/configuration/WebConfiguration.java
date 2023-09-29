package com.rf.blogapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableWebSecurity
@RestController
public class WebConfiguration {
    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authentication)->
                authentication.requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/deneme")).
                       authenticated()
                        .anyRequest().permitAll()
                );
        //http.httpBasic(Customizer.withDefaults());
        // security hatalarını almak için aşağıdaki gibi
        http.httpBasic(httpBasic->httpBasic.authenticationEntryPoint(authEntryPoint));
        http.csrf(csrt->csrt.disable());
        http.headers(headers->headers.disable());
        return http.build();
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @GetMapping("/deneme")
    public String naber(){
        return "Selam";
    }
}
