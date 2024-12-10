package com.example.wsd_crawling.applications.service;

import com.example.wsd_crawling.applications.model.Application;
import com.example.wsd_crawling.applications.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public boolean hasAlreadyApplied(Long userId, Long jobPostingId) {
        return applicationRepository.findByUserIdAndJobPostingId(userId, jobPostingId).isPresent();
    }

    public Application applyForJob(Long userId, Long jobPostingId, String resume) {
        if (hasAlreadyApplied(userId, jobPostingId)) {
            throw new IllegalArgumentException("User has already applied for this job.");
        }

        Application application = new Application();
        application.setUserId(userId);
        application.setJobPostingId(jobPostingId);
        application.setResume(resume);

        return applicationRepository.save(application);
    }
}
