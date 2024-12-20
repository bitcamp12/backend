package com.example.demo.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 데이터 저장
    public void saveToken(String key, String value, long expirationInMillis) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expirationInMillis));
    }

    // 데이터 조회
    public String getToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 데이터 삭제
    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
}

