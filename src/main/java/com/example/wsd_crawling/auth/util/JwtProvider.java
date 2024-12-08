package com.example.wsd_crawling.auth.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
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
        System.out.println("JWT Secret Key: " + secret);
        if (secret.length() * 8 < 512) {
            throw new IllegalArgumentException("The secret key must be at least 512 bits for HS512.");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
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
    public String createRefreshToken(String username) {
        Date issuedAt = new Date();
        Date expiration = new Date(System.currentTimeMillis() + refreshExpirationMs);
        System.out.println("Refresh Token 생성 - 발급 시각: " + issuedAt + ", 만료 시각: " + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }


    // Access Token 갱신
    public String refreshAccessToken(String refreshToken) {
        try {
            if (!validateToken(refreshToken)) {
                throw new IllegalArgumentException("Invalid Refresh Token.");
            }
            String email = getUserEmailFromToken(refreshToken); // Refresh Token에서 사용자 이메일 추출
            return createAccessToken(email); // 새로운 Access Token 생성
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Refresh Token has expired.", e);
        } catch (JwtException e) {
            throw new IllegalArgumentException("Error while refreshing Access Token.", e);
        }
    }

    // JWT Token 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            System.out.println("Valid Token: " + token); // 성공 로그 추가
            return true;
        } catch (SecurityException e) {
            System.out.println("Invalid JWT signature: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.out.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty: " + e.getMessage());
        } catch (JwtException e) {
            System.out.println("Token validation error: " + e.getMessage());
        }
        return false;
    }


    // JWT Token에서 사용자 이메일 추출
    public String getUserEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject(); // subject 필드를 사용자 이메일로 사용
    }
}
