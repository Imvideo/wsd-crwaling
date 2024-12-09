package com.example.wsd_crawling.jobs.repository;

import com.example.wsd_crawling.jobs.model.JobPostingStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatsRepository extends JpaRepository<JobPostingStats, Long> {

    Optional<JobPostingStats> findByJobPostingId(Long jobPostingId);

}
