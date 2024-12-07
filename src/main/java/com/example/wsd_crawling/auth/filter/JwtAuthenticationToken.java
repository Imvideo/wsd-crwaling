package com.example.wsd_crawling.auth.filter;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String principal;

    public JwtAuthenticationToken(String principal, Collection<SimpleGrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null; // 토큰이 아닌 자격 증명은 필요 없으므로 null 반환
    }

    @Override
    public Object getPrincipal() {
        return principal; // 사용자명 반환
    }
}
