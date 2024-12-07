package com.example.wsd_crawling.auth.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String SECRET_KEY = "your_secret_key"; // 비밀 키

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " 부분 제거

            try {
                // JWT 토큰을 파싱하여 Claims 정보 추출
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY.getBytes()) // 시크릿 키를 byte 배열로 제공
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // 토큰에서 사용자명과 권한 정보 추출
                String username = claims.getSubject();
                String role = claims.get("role", String.class); // JWT에서 role 정보 추출

                if (username != null && role != null) {
                    Authentication authentication = new JwtAuthenticationToken(
                            username,
                            Collections.singletonList(new SimpleGrantedAuthority(role))
                    );

                    // SecurityContext에 인증 정보 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                // 토큰 검증 실패 시 에러 처리
                System.err.println("Invalid JWT Token: " + e.getMessage());
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
