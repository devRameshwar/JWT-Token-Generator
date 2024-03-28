package com.jwt.service;

import com.jwt.dto.LoginRequest;
import com.jwt.dto.UserInfoDto;
import com.jwt.model.UserInfo;
import com.jwt.repository.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService  implements UserDetailsService {
    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper mapper;

    public String registration( UserInfoDto request) {
        UserInfo user = new UserInfo();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        mapper.map(request,user);
        repository.save(user);
        return "User registration successfully done...";
    }

   /* public String logIn(String name) {
        //TODO:we can write login logic here with the help of spring security
        UserDetailsService userDetailsService=(username)->
                repository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        userDetailsService.loadUserByUsername(name);
        return null;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       /*TODO: this is an abstract Method define in UserDetailsService functional interface we can also use lambda expression */
        UserDetails userDetails = repository.findByEmail(username).orElseThrow(() -> new RuntimeException("User not found"));
        return userDetails;
    }
}
