package com.example.wsd_crawling.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecretKey secretKey;

    // SecretKey를 @Value로 주입받아 생성
    public JwtAuthenticationFilter(@Value("${jwt.secret}") String secret) {
        // Base64 디코딩 후 SecretKey 생성
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            try {
                System.out.println("Raw Token: " + token);

                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey) // SecretKey를 사용하여 토큰 서명 검증
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String username = claims.getSubject();
                System.out.println("JWT Claims: " + claims);
                System.out.println("Extracted Username: " + username);

                if (username != null) {
                    Authentication authentication = new JwtAuthenticationToken(
                            username,
                            Collections.emptyList()
                    );

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("Authentication set for: " + username);
                }
            } catch (Exception e) {
                System.err.println("Invalid JWT Token: " + e.getMessage());
                e.printStackTrace(); // 예외 스택 트레이스 출력
            }

        }

        filterChain.doFilter(request, response);
    }

}
