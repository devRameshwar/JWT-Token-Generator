package com.jwt.security.jwtAuthentication;

import com.jwt.security.jwtToken.ApplicationTokenGeneretor;
import com.jwt.service.UserInfoService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        logger.info("Inside the JwtAuthenticationFilter in doFilterCain Method..");
        /*TODO: First: we check in request Token is available or Not...*/
        String token = request.getHeader("header");
        logger.info("Token shered by user " + token);
        /*TODO: after getting token we will extract username with help of request token */
        String userNameFromToken = null;
        if (token == null) {
            logger.info("In request token is empty Please come with token.");
        } else {
            /*extracting UserName in with token..*/
            userNameFromToken = tokenGeneretor.getUserNameFromToken(token);
            logger.info("User Name is " + userNameFromToken);
        }
        /*TODO: validate Token user name and clint request name*/
        /*TODO: check null*/
        if (userNameFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            /*TODO:now checking details & get data from database passing user name*/
            UserDetails userDetails = service.loadUserByUsername(userNameFromToken);
            /*token valediction with respect to database user details and time details.
             * this is already defined in ApplicationTokenGeneretor class*/
            Boolean tokenVailed = tokenGeneretor.isTokenVailed(userDetails.getUsername(), token);
            /*tokenVailed is true then we give access
             * if tokenVailed is false then give a re-login message*/
            logger.info(String.valueOf(tokenVailed));
            if (tokenVailed) {
                /*if true then create a security token*/
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                /*Handover token to authentication to security context*/
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //TODO: security heck is completed.
            } else {
                logger.info("Token is invalided please  try with a vailed token");
            }

        }
        filterChain.doFilter(request, response);

    }
}
