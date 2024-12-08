package com.example.wsd_crawling.auth.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    public JwtAuthenticationToken(String principal, Collection<SimpleGrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.setAuthenticated(true); // 인증된 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return null; // JWT 기반이므로 자격 증명이 필요 없음
    }

    @Override
    public Object getPrincipal() {
        return principal; // 사용자명 반환
    }

//    @Override
//    public void setAuthenticated(boolean isAuthenticated) {
//        if (isAuthenticated) {
//            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor instead.");
//        }
//        super.setAuthenticated(false);
//    }
}
