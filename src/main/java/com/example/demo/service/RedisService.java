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
        try {
            redisTemplate.opsForValue().set(key, value, Duration.ofMillis(expirationInMillis));
        } catch (Exception e) {
            // Redis 저장 실패 시, 로그에만 기록하고 예외를 던지지 않음
            System.err.println("Redis 저장 실패: " + e.getMessage());
        }
    }

    // 데이터 조회
    public String getToken(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            System.err.println("Redis 조회 실패: " + e.getMessage());
            return null;
        }
    }

    // 데이터 삭제
    public void deleteToken(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            System.err.println("Redis 삭제 실패: " + e.getMessage());
        }
    }
}
