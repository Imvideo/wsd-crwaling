package com.example.wsd_crawling.auth.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {

    private final ConcurrentHashMap<String, Boolean> tokenStore = new ConcurrentHashMap<>();

    // Refresh Token 저장
    public void storeToken(String token) {
        tokenStore.put(token, true); // 유효 상태 저장
        System.out.println("저장된 Refresh Token: '" + token + "'"); // 디버깅 로그
    }

    // Refresh Token 검증
    public boolean isTokenValid(String token) {
        token = token.trim(); // 공백 제거
        System.out.println("저장된 토큰 목록: " + tokenStore.keySet());
        System.out.println("검증하려는 토큰: '" + token + "'");
        boolean isValid = tokenStore.getOrDefault(token, false);
        System.out.println("토큰 유효 여부: " + isValid);
        return isValid;
    }

    // Refresh Token 무효화
    public void invalidateToken(String token) {
        token = token.trim(); // 공백 제거
        if (tokenStore.containsKey(token)) {
            tokenStore.remove(token);
            System.out.println("무효화된 토큰: '" + token + "'");
        } else {
            System.out.println("무효화하려는 토큰이 존재하지 않음: '" + token + "'");
        }
    }
}
