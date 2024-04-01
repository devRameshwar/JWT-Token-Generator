package com.jwt.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_information")
public class UserInfo implements UserDetails {

    @Enumerated(EnumType.STRING)
    private Role role;
    @Id
    @Column
    private Integer userId;
    @Column
    private String userName;
    @Column
    private String email;
    @Column
    private String mobileNumber;
    @Column
    private String password;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Here we decide role-based Authorization
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @Override
    public String getUsername() {
        //Here decide what field in entity is your username we can pass it
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        //Here we write the logic account is expired or not to database flag
        // (a flag is a field in database to check Expire)
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
