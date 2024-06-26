package com.jwt.dto;

import com.jwt.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {

    private Integer userId;

    private String userName;

    private String email;

    private String mobileNumber;

    private String password;

    private Role role;


}
