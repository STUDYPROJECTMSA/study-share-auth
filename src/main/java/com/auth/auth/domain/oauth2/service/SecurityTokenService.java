package com.auth.auth.domain.oauth2.service;

import com.auth.auth.domain.oauth2.dto.SecurityToken;
import org.springframework.stereotype.Component;

@Component
public interface SecurityTokenService {
    SecurityToken generateToken(String email, String role);

    String tokenGetEmail(String token);

    boolean checkExpiredRefreshToken(String refreshToken);

}
