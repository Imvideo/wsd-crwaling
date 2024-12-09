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

    public Page<Job> getFilteredJobs(String location, String employmentType, String sector, Integer minSalary, Integer maxSalary, Pageable pageable) {
        return jobRepository.findByLocationContainingAndEmploymentTypeContainingAndSectorContainingAndSalaryBetween(
                location,
                employmentType,
                sector,
                minSalary,
                maxSalary,
                pageable
        );
    }

    public Page<Job> searchJobs(String keyword, String location, String employmentType, String sector, Integer minSalary, Integer maxSalary, Pageable pageable) {
        return jobRepository.findByTitleContainingAndLocationContainingAndEmploymentTypeContainingAndSectorContainingAndSalaryBetween(
                keyword,
                location,
                employmentType,
                sector,
                minSalary,
                maxSalary,
                pageable
        );
    }

    public Page<Job> getJobsSortedByCreatedAt(Pageable pageable) {
        return jobRepository.findAllByOrderByCreatedAtDesc(pageable);
    }
}
