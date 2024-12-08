package com.example.wsd_crawling.auth.controller;

import com.example.wsd_crawling.auth.model.User;
import com.example.wsd_crawling.auth.model.UserRegistrationRequest;
import com.example.wsd_crawling.auth.model.UserLoginRequest;
import com.example.wsd_crawling.auth.repository.UserRepository;
import com.example.wsd_crawling.auth.service.UserService;
import com.example.wsd_crawling.auth.service.RefreshTokenService;
import com.example.wsd_crawling.auth.util.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            String result = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 실패: " + e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequest request) {
        try {
            // 로그인 처리 및 토큰 생성
            Map<String, String> tokens = userService.login(request);

            // 성공 응답
            return ResponseEntity.ok(tokens);
        } catch (IllegalArgumentException e) {
            // 인증 실패
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인 실패: " + e.getMessage());
        }
    }




    // 토큰 갱신
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        try {
            // Refresh Token 검증
            if (!refreshTokenService.isTokenValid(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh Token이 유효하지 않습니다.");
            }

            // Access Token 갱신
            String newAccessToken = jwtProvider.refreshAccessToken(refreshToken);

            // Refresh Token 사용 후 무효화
            refreshTokenService.invalidateToken(refreshToken);

            // 새로운 Refresh Token 발급 및 저장
            String newRefreshToken = jwtProvider.createRefreshToken(jwtProvider.extractEmailFromToken(refreshToken));
            refreshTokenService.storeToken(newRefreshToken);

            // 응답 데이터 구성
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);
            tokens.put("refreshToken", newRefreshToken);

            return ResponseEntity.ok(tokens);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("토큰 갱신 실패: " + e.getMessage());
        }
    }

    // 회원 정보 수정
    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(
            @AuthenticationPrincipal User currentUser,
            @RequestBody UserRegistrationRequest request
    ) {
        try {
            userService.updateProfile(currentUser, request);
            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정 실패: " + e.getMessage());
        }
    }
}
