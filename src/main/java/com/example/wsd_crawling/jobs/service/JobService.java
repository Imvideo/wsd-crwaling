package com.example.wsd_crawling.jobs.service;

import com.example.wsd_crawling.jobs.model.Job;
import com.example.wsd_crawling.jobs.repository.JobRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    // ID로 공고 조회
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    // 조회수 증가
    public void incrementViewCount(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job != null) {
            job.setViewCount(job.getViewCount() + 1);
            jobRepository.save(job);
        }
    }

    // 관련 공고 추천
    public List<Job> getRelatedJobs(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null) {
            return List.of();
        }

        // 동일한 분야(Sector)에서 최대 5개의 공고를 추천
        return jobRepository.findBySectorContainingAndIdNot(job.getSector(), jobId, PageRequest.of(0, 5));
    }
}
