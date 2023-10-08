package com.rf.blogapp.compenent;

import com.rf.blogapp.entity.User;
import com.rf.blogapp.exception.ActivationTokenException;
import com.rf.blogapp.exception.LoginActivationTokenException;
import com.rf.blogapp.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    @Autowired
    TokenService tokenService;

    @Qualifier("handlerExceptionResolver")
    @Autowired
    private HandlerExceptionResolver exceptionResolver;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String prefix=getToken(request);

        if (prefix!=null){
            System.out.println("PREFİX------"+prefix);
           User user= tokenService.verifyToken(prefix);
        if(user!=null){
            if(user.isActive()==false){
                exceptionResolver.resolveException(request,response,null,new LoginActivationTokenException());
                return;
            }else{
                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        }
        filterChain.doFilter(request,response);
    }
    private String getToken(HttpServletRequest request){
        String prefix=request.getHeader("Authorization");
        var cookies=request.getCookies();
        if(cookies==null) return prefix;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("furkancan-token") && (cookie.getValue() != null || !cookie.getValue().isEmpty()))
            return "Prefix " + cookie.getValue();
        }

        return prefix;

    }
}
