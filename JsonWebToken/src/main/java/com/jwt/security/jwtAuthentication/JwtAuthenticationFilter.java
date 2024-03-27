package com.jwt.security.jwtAuthentication;

import com.jwt.security.jwtToken.ApplicationTokenGeneretor;
import com.jwt.service.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    /*OncePerRequestFilter is an abstract method; it will execute every request to filter with the help of header token */
    @Autowired
    private ApplicationTokenGeneretor tokenGeneretor;

    @Autowired
    private UserInfoService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

    }
}
