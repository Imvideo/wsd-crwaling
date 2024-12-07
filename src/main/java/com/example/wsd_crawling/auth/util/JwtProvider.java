package com.example.wsd_crawling.auth.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    private String secretKey = "secretKey";  // 실제 비즈니스에서는 더 안전한 방법으로 관리해야 함

    // JWT 토큰 생성
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))  // 1일 만료
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // JWT 토큰 검증
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Refresh 토큰을 이용해 새로운 Access 토큰 발급
    public String refreshAccessToken(String refreshToken) {
        // Refresh Token을 기반으로 새로운 Access Token 발급
        return generateJwtToken("user");
    }
}
