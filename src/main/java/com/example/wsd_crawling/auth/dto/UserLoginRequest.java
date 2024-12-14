package com.example.wsd_crawling.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "사용자 로그인 요청 DTO")
public class UserLoginRequest {

    @Schema(description = "사용자 이메일", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "사용자 비밀번호", example = "password123", required = true)
    private String password;

    // 기본 생성자
    public UserLoginRequest() {
    }

    // 생성자
    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getter 및 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
