package com.example.demo.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // 캐시별로 다른 만료 시간을 설정
        cacheManager.registerCustomCache("play", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)  // 1분 만료
                .maximumSize(100)
                .build());
        
        cacheManager.registerCustomCache("PlayRandom", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES) // 1분 만료
                .maximumSize(50)
                .build());

        cacheManager.registerCustomCache("calculateDiscount", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES) // 1분 만료
                .maximumSize(100)
                .build());

        cacheManager.registerCustomCache("getPlayAll", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES) // 1분 만료 
                .maximumSize(100)
                .build());

        cacheManager.registerCustomCache("getPlaysEndingSoon", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)  // 1분 만료
                .maximumSize(100)
                .build());

        cacheManager.registerCustomCache("getPlaysComingSoon", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES)  // 1분 만료
                .maximumSize(100)
                .build());

        cacheManager.registerCustomCache("getPlaysLimited", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES) // 1분 만료
                .maximumSize(100)
                .build());
        
        cacheManager.registerCustomCache("searchListEntity", Caffeine.newBuilder()
                .expireAfterWrite(2, TimeUnit.MINUTES) // 1분 만료
                .maximumSize(100)//사이즈 넘어가면 삭제
                .build());
                
        cacheManager.registerCustomCache("searchList", Caffeine.newBuilder()
                        .expireAfterWrite(2, TimeUnit.MINUTES) // 1분 만료
                        .maximumSize(100)
                        .build());

        return cacheManager;
    }
}

