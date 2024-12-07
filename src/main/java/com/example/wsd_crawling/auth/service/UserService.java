package com.example.wsd_crawling.auth.service;

import com.example.wsd_crawling.auth.model.User;
import com.example.wsd_crawling.auth.model.UserLoginRequest;
import com.example.wsd_crawling.auth.model.UserRegistrationRequest;
import com.example.wsd_crawling.auth.repository.UserRepository;
import com.example.wsd_crawling.auth.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 비밀번호 암호화

    @Autowired
    private JwtProvider jwtProvider; // JWT 토큰 생성기

    // 회원 가입
    public String registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 생성자를 사용해 User 객체 생성
        User user = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()), // 비밀번호 암호화
                request.getName()
        );

        userRepository.save(user);
        return "회원가입 성공";
    }


    // 로그인
    public String login(UserLoginRequest request) {
        // 사용자 인증
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        // JWT Access Token 생성
        return jwtProvider.createAccessToken(user.getEmail());
    }

    // 회원 정보 수정
    public String updateProfile(UserRegistrationRequest request) {
        // 사용자 조회
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 비밀번호 변경 (옵션)
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // 이름 변경
        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }

        // 업데이트 저장
        userRepository.save(user);
        return "회원 정보 수정 성공";
    }
}
