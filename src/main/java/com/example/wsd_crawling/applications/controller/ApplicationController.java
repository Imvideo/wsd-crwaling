package com.example.wsd_crawling.applications.controller;

import com.example.wsd_crawling.applications.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
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
            applicationService.apply(request.getUserId(), request.getJobPostingId());
            return ResponseEntity.ok("지원 완료");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

// 요청 DTO
class ApplicationRequest {
    private Long userId;
    private Long jobPostingId;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJobPostingId() {
        return jobPostingId;
    }

    public void setJobPostingId(Long jobPostingId) {
        this.jobPostingId = jobPostingId;
    }
}
