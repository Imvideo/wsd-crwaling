package com.example.wsd_crawling.auth.service;

import com.example.wsd_crawling.auth.model.UserRegistrationRequest;
import com.example.wsd_crawling.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 회원 가입
    public String registerUser(UserRegistrationRequest request) {
        // 이메일 형식 검증 및 사용자 저장 로직
        return "User Registered Successfully";
    }

    // 로그인
    public String login(UserRegistrationRequest request) {
        // 로그인 처리 및 JWT 발급
        return "JWT Token";
    }

    // 회원 정보 수정
    public String updateProfile(UserRegistrationRequest request) {
        // 사용자 정보 수정 로직
        return "Profile Updated Successfully";
    }
}
