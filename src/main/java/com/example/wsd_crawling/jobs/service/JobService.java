package com.example.wsd_crawling.jobs.service;

import com.example.wsd_crawling.jobs.dto.JobResponse;
import com.example.wsd_crawling.jobs.model.Job;
import com.example.wsd_crawling.jobs.model.JobPostingStats;
import com.example.wsd_crawling.jobs.repository.JobRepository;
import com.example.wsd_crawling.jobs.repository.StatsRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private StatsRepository statsRepository;

    public Page<JobResponse> filterJobs(String keyword, String location, String employmentType, String sector, Integer minSalary, Integer maxSalary, Pageable pageable) {
        Page<Job> jobs = jobRepository.findByTitleContainingAndLocationContainingAndEmploymentTypeContainingAndSectorContainingAndSalaryBetween(
                keyword,
                location,
                employmentType,
                sector,
                minSalary,
                maxSalary,
                pageable
        );
        return jobs.map(JobResponse::new);
    }

    public Page<JobResponse> searchJobs(String keyword, String location, String employmentType, String sector, Integer minSalary, Integer maxSalary, Pageable pageable) {
        return filterJobs(keyword, location, employmentType, sector, minSalary, maxSalary, pageable);
    }

    public Page<JobResponse> getFilteredJobs(String location, String employmentType, String sector, Integer minSalary, Integer maxSalary, Pageable pageable) {
        return filterJobs("", location, employmentType, sector, minSalary, maxSalary, pageable);
    }

    public JobResponse getJobById(Long id) {
        Job job = jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + id));
        return new JobResponse(job);
    }

    public void incrementViewCount(Long jobId) {
        if (!statsRepository.existsByJobPostingId(jobId)) {
            JobPostingStats stats = new JobPostingStats();
            stats.setJobPostingId(jobId);
            stats.setViewCount(1);
            statsRepository.save(stats);
        } else {
            statsRepository.incrementViewCount(jobId);
        }
    }

    public List<JobResponse> getRelatedJobs(Long jobId) {
        return jobRepository.findById(jobId)
                .filter(job -> job.getSector() != null && !job.getSector().isEmpty())
                .map(job -> jobRepository.findBySectorContainingAndIdNot(job.getSector(), jobId, PageRequest.of(0, 3))
                        .stream()
                        .map(JobResponse::new)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }
}

