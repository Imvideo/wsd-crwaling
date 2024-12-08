package com.example.wsd_crawling.jobs.service;

import com.example.wsd_crawling.jobs.model.Job;
import com.example.wsd_crawling.jobs.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Page<Job> getFilteredJobs(String location, String experience, String salary, String technologyStack, Pageable pageable) {
        return jobRepository.findByLocationContainingAndExperienceContainingAndSalaryContainingAndTechnologyStackContaining(
                location,
                experience,
                salary,
                technologyStack,
                pageable
        );
    }

    // 검색 및 필터링 메서드 추가
    public Page<Job> searchJobs(String keyword, String location, String experience, String salary, String technologyStack, Pageable pageable) {
        // 모든 조건에 키워드 검색 추가
        return jobRepository.findByTitleContainingAndLocationContainingAndExperienceContainingAndSalaryContainingAndTechnologyStackContaining(
                keyword,
                location,
                experience,
                salary,
                technologyStack,
                pageable
        );
    }

    public Page<Job> getJobsSortedByCreatedAt(Pageable pageable) {
        return jobRepository.findAllByOrderByCreatedAtDesc(pageable);
    }
}
