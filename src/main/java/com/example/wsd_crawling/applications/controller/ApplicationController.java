package com.example.wsd_crawling.applications.controller;

import com.example.wsd_crawling.applications.model.Application;
import com.example.wsd_crawling.applications.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<?> applyForJob(
            @RequestParam Long userId,
            @RequestParam Long jobPostingId,
            @RequestParam(required = false) MultipartFile resumeFile
    ) {
        try {
            String resumeContent = null;
            if (resumeFile != null) {
                // 이력서 파일을 문자열로 변환 (간단한 처리)
                resumeContent = new String(resumeFile.getBytes());
            }

            Application application = applicationService.applyForJob(userId, jobPostingId, resumeContent);

            return ResponseEntity.ok(application);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to process resume file.");
        }
    }
}
