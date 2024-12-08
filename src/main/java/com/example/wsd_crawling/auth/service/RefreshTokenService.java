package com.example.wsd_crawling.auth.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class RefreshTokenService {

    private final ConcurrentHashMap<String, Boolean> tokenStore = new ConcurrentHashMap<>();

    // Refresh Token 저장
    public void storeToken(String token) {
        tokenStore.put(token, true); // 유효 상태 저장
        System.out.println("Stored Refresh Token: " + token); // 로그 추가
    }

    // Refresh Token 검증
    public boolean isTokenValid(String token) {
        System.out.println("현재 저장된 토큰 목록: " + tokenStore); // 저장된 토큰 상태를 출력
        boolean isValid = tokenStore.getOrDefault(token, false);
        System.out.println("검증하려는 토큰: " + token);
        System.out.println("토큰 유효 여부: " + isValid);
        return isValid;
    }



    // Refresh Token 무효화
    public void invalidateToken(String token) {
        tokenStore.remove(token); // 사용 후 제거
    }
}
