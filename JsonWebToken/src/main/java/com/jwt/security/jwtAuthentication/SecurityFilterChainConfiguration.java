package com.jwt.security.jwtAuthentication;

import com.jwt.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainConfiguration {

    Logger logger = LoggerFactory.getLogger(SecurityFilterChainConfiguration.class);
    @Autowired
    private JwtAuthenticationFilter tokenFilter;
    @Autowired
    private UserInfoService service;
    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        logger.info("BCryptPasswordEncoder is called..");
        return new BCryptPasswordEncoder();
    }

    /*Todo: creating a bean for AuthenticationManager Interface with help of AuthenticationConfiguration class */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        logger.info("AuthenticationManager object is created.");
        return configuration.getAuthenticationManager();
    }

    /*todo: we are Enable to SecurityFilterChain for modify which urls is public and which urls is protected*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        logger.info("SecurityFilterChain is executed..." + HttpSecurity.class);
        return httpSecurity.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/registration/**", "/logIn/**")
                        .permitAll()
                        .requestMatchers("key/user/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("key/getvailedity/**").hasAnyAuthority("USER")
                        .anyRequest().authenticated())
                .userDetailsService(service)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(this.tokenFilter, UsernamePasswordAuthenticationFilter.class).build();

    }
}
