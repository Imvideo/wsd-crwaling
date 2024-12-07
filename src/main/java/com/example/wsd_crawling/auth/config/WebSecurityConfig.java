package com.example.wsd_crawling.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    // PasswordEncoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호를 암호화할 BCryptPasswordEncoder 사용
    }

    // SecurityFilterChain Bean for HTTP security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/auth/login", "/auth/register").permitAll()  // 로그인과 회원가입은 누구나 접근 가능
                                .anyRequest().authenticated()  // 나머지 요청은 인증 필요
                )
                .addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);  // JWT 인증 필터 추가

        return http.build();  // HttpSecurity 설정 반환
    }

    // UserDetailsService Bean (사용자 정보 서비스)
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();  // 사용자 정보를 반환하는 서비스
    }
}
