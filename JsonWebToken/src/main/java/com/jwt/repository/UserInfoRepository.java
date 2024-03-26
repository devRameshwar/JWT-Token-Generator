package com.jwt.repository;

import com.jwt.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository  extends JpaRepository<UserInfo,Integer> {
    Optional<UserDetails> findByEmail(String email);
}
