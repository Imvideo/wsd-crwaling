package com.example.wsd_crawling.applications.service;

import com.example.wsd_crawling.applications.model.Application;
import com.example.wsd_crawling.applications.repository.ApplicationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public void apply(Long userId, Long jobPostingId) {
        // 중복 지원 체크
        if (applicationRepository.findByUserIdAndJobPostingId(userId, jobPostingId).isPresent()) {
            throw new IllegalStateException("이미 해당 공고에 지원하셨습니다.");
        }

        // 지원 정보 저장
        Application application = new Application();
        application.setUserId(userId);
        application.setJobPostingId(jobPostingId);
        application.setApplicationDate(LocalDateTime.now());
        application.setStatus("APPLIED");

        applicationRepository.save(application);
    }

    // 사용자별 지원 목록 조회
    public Page<Application> getApplicationsByUser(Long userId, String status, Pageable pageable) {
        if (status == null || status.isEmpty()) {
            return applicationRepository.findByUserId(userId, pageable); // 상태 필터링 X
        } else {
            return applicationRepository.findByUserIdAndStatus(userId, status, pageable); // 상태 필터링 O
        }
    }
}
