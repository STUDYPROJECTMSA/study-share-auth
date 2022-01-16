package com.auth.auth.global.util.redis.impl;

import com.auth.auth.global.util.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate redisTemplate;

    @Override
    public void saveToken(String email, String token) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set(email, token);
        redisTemplate.expire(email, 1000000000, TimeUnit.DAYS);
    }

    @Override
    public void deleteToken(String email) {
        log.info("delete to refresh token");
        redisTemplate.delete(email);
    }

    @Override
    public String findByEmail(String email) {
        log.info("get refresh token");
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String token = vop.get(email);
        return token;
    }
}
