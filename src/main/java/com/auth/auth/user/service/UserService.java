package com.auth.auth.user.service;

import com.auth.auth.oauth2.dto.SecurityToken;
import com.auth.auth.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    SecurityToken refreshService(String expiredAccessToken, String refreshToken) throws Exception;

    UserDto loginService(String code);

}
