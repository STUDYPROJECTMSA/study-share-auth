package com.auth.auth.global.util.redis;

import org.springframework.stereotype.Component;

@Component
public interface RedisService {
    void saveToken(String email, String token);
    void deleteToken(String email);
    String findByEmail(String email);
}
