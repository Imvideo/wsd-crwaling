package com.example.wsd_crawling.auth.controller;

import com.example.wsd_crawling.auth.model.UserRegistrationRequest;
import com.example.wsd_crawling.auth.service.UserService;
import com.example.wsd_crawling.auth.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    // 회원 가입
    @PostMapping("/register")
    public String register(@RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody UserRegistrationRequest request) {
        return userService.login(request);
    }

    // 토큰 갱신
    @PostMapping("/refresh")
    public String refreshToken(@RequestBody String refreshToken) {
        return jwtProvider.refreshAccessToken(refreshToken);
    }

    // 회원 정보 수정
    @PutMapping("/profile")
    public String updateProfile(@RequestBody UserRegistrationRequest request) {
        return userService.updateProfile(request);
    }
}
