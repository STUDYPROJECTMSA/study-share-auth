package com.auth.auth.domain.user.dto;

import com.auth.auth.domain.user.domain.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private String imageUrl;

    private Role role;

    private String token;

    private String refreshToken;
}
