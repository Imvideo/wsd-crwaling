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

    public Page<Job> getFilteredJobs(String location, String experience, String salary, Pageable pageable) {
        return jobRepository.findByLocationContainingAndExperienceContainingAndSalaryContaining(
                location != null ? location : "",
                experience != null ? experience : "",
                salary != null ? salary : "",
                pageable
        );
    }

    public Page<Job> searchJobs(String query, Pageable pageable) {
        return jobRepository.findByTitleContainingOrCompanyContainingOrPositionContaining(
                query, query, query, pageable
        );
    }
}
