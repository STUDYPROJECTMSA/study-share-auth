package com.auth.auth.user.dto;

import com.auth.auth.user.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

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
