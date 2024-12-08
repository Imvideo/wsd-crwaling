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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;

    // 회원 가입
    public String registerUser(UserRegistrationRequest request) {
        String email = request.getEmail();

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("유효하지 않은 이메일 형식입니다.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        User user = new User(
                email,
                passwordEncoder.encode(request.getPassword()),
                request.getName()
        );

        userRepository.save(user);
        return "회원가입 성공";
    }

    // 이메일 형식 검증
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    // 로그인
    public String login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 잘못되었습니다.");
        }

        // Access 및 Refresh Token 생성
        String accessToken = jwtProvider.createAccessToken(user.getEmail());
        String refreshToken = jwtProvider.createRefreshToken(user.getEmail());

        // Refresh Token 저장
        refreshTokenService.storeToken(refreshToken);

        // Access 토큰과 Refresh 토큰 반환 (예: JSON 형태로 반환)
        return "{ \"accessToken\": \"" + accessToken + "\", \"refreshToken\": \"" + refreshToken + "\" }";
    }

    // 회원 정보 수정
    public String updateProfile(User currentUser, UserRegistrationRequest request) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        if (request.getName() != null && !request.getName().isEmpty()) {
            user.setName(request.getName());
        }

        userRepository.save(user);
        return "회원 정보 수정 성공";
    }

    // Refresh Token 검증 및 Access Token 갱신
    public String refreshToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh 토큰입니다.");
        }

        String userEmail = jwtProvider.getUserEmailFromToken(refreshToken);
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return jwtProvider.createAccessToken(user.getEmail());
    }
}
