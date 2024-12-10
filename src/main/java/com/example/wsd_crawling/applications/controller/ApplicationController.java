package com.example.wsd_crawling.applications.controller;

import com.example.wsd_crawling.applications.dto.ApplicationRequest;
import com.example.wsd_crawling.applications.model.Application;
import com.example.wsd_crawling.applications.service.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<String> apply(@RequestBody ApplicationRequest request) {
        try {
            applicationService.apply(request.getUserId(), request.getJobPostingId(), request.getStatus());
            return ResponseEntity.ok("지원이 성공적으로 제출되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    // 지원 내역 조회 API
    @GetMapping
    public ResponseEntity<Page<Application>> getApplications(
            @RequestParam Long userId, // 사용자 ID 필수
            @RequestParam(required = false) String status, // 상태 필터링 (선택)
            @RequestParam(defaultValue = "0") int page, // 페이지 번호 (기본값 0)
            @RequestParam(defaultValue = "10") int size // 페이지 크기 (기본값 10)
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Application> applications = applicationService.getApplicationsByUser(userId, status, pageable);
        return ResponseEntity.ok(applications);
    }

    // 지원 취소 API
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deletePendingApplications(@PathVariable Long userId) {
        boolean isDeleted = applicationService.deletePendingApplicationsByUserId(userId);
        if (isDeleted) {
            return ResponseEntity.ok("Pending applications deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No pending applications to delete.");
        }
    }






}


