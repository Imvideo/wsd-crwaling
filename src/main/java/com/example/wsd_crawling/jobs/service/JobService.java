package com.example.wsd_crawling.jobs.service;

import com.example.wsd_crawling.jobs.model.Job;
import com.example.wsd_crawling.jobs.model.JobPostingStats;
import com.example.wsd_crawling.jobs.repository.JobRepository;
import com.example.wsd_crawling.jobs.repository.StatsRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StatsRepository statsRepository;

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

    // ID로 공고 조회
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    // 조회수 증가
    public void incrementViewCount(Long jobId) {
        JobPostingStats stats = statsRepository.findByJobPostingId(jobId).orElse(null);
        if (stats == null) {
            stats = new JobPostingStats();
            stats.setJobPostingId(jobId);
            stats.setViewCount(1); // 처음 조회 시 1로 초기화
        } else {
            stats.setViewCount(stats.getViewCount() + 1); // 기존 조회수 증가
        }
        statsRepository.save(stats);
    }


    // 관련 공고 추천
    public List<Job> getRelatedJobs(Long jobId) {
        Job job = jobRepository.findById(jobId).orElse(null);
        if (job == null || job.getSector() == null || job.getSector().isEmpty()) {
            return List.of(); // 관련 공고가 없으면 빈 리스트 반환
        }

        // 동일한 분야(Sector)에서 최대 3개의 공고 추천
        return jobRepository.findBySectorContainingAndIdNot(job.getSector(), jobId, PageRequest.of(0, 3));
    }


}
