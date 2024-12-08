package com.example.wsd_crawling.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    private final SecretKey secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${jwt.refreshExpiration}")
    private long refreshExpirationMs;

    public JwtProvider(@Value("${jwt.secret}") String secret) {
        try {
            // Base64 디코딩 후 SecretKey 생성
            byte[] decodedKey = Base64.getDecoder().decode(secret);
            if (decodedKey.length < 64) { // 최소 길이 검증 (64 bytes = 512 bits)
                throw new IllegalArgumentException("The decoded key must be at least 64 bytes (512 bits).");
            }
            this.secretKey = Keys.hmacShaKeyFor(decodedKey);
            System.out.println("JWT Secret Key Initialized Successfully.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 encoded key. Ensure it's a valid Base64-encoded string.", e);
        }
    }

    // Access Token 생성
    public String createAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email) // 사용자 이메일을 subject로 설정
                .setIssuedAt(new Date()) // 토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // 만료 시간 설정
                .signWith(secretKey, SignatureAlgorithm.HS512) // 서명 알고리즘
                .compact();
    }

    // Refresh Token 생성
    public String createRefreshToken(String email) {
        Date issuedAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + refreshExpirationMs);
        System.out.println("Refresh Token 생성: 발급 시각=" + issuedAt + ", 만료 시각=" + expiration); // 디버그 로그

        return Jwts.builder()
                .setSubject(email) // 사용자 이메일을 subject로 설정
                .setIssuedAt(issuedAt) // 발급 시각
                .setExpiration(expiration) // 만료 시각
                .signWith(secretKey, SignatureAlgorithm.HS512) // 서명
                .compact();
    }

    // Access Token 갱신
    public String refreshAccessToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid Refresh Token.");
        }

        String email = extractEmailFromToken(refreshToken); // Refresh Token에서 이메일 추출
        return createAccessToken(email); // 새로운 Access Token 생성
    }

    // JWT Token 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            System.out.println("유효한 토큰: " + token); // 성공 로그
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT 토큰이 만료됨: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("JWT 토큰 검증 실패: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 비어있음: " + e.getMessage());
        }
        return false;
    }

    // JWT Token에서 사용자 이메일 추출
    public String extractEmailFromToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject(); // Subject에서 이메일 추출
        } catch (JwtException e) {
            throw new IllegalArgumentException("토큰에서 이메일을 추출할 수 없습니다: " + e.getMessage(), e);
        }
    }
}
