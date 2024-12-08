package com.example.wsd_crawling.auth.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {

    private final ConcurrentHashMap<String, Boolean> tokenStore = new ConcurrentHashMap<>();

    // Refresh Token 저장
    public void storeToken(String token) {
        tokenStore.put(token, true); // 유효 상태 저장
    }

    // Refresh Token 검증
    public boolean isTokenValid(String token) {
        return tokenStore.getOrDefault(token, false); // 유효한지 확인
    }

    // Refresh Token 무효화
    public void invalidateToken(String token) {
        tokenStore.remove(token); // 사용 후 제거
    }
}
