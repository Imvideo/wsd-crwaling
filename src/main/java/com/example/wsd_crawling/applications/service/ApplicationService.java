package com.example.wsd_crawling.applications.service;

import com.example.wsd_crawling.applications.model.Application;
import com.example.wsd_crawling.applications.repository.ApplicationRepository;
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

        // 새로운 지원 저장
        Application application = new Application();
        application.setUserId(userId);
        application.setJobPostingId(jobPostingId);
        application.setApplicationDate(LocalDateTime.now());
        application.setStatus("PENDING");

        applicationRepository.save(application);
    }
}
