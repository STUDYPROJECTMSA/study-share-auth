package com.auth.auth.domain.user.service;

import com.auth.auth.domain.oauth2.dto.SecurityToken;
import com.auth.auth.domain.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    SecurityToken refreshService(String expiredAccessToken, String refreshToken) throws Exception;

    UserDto loginService(String code);

}
