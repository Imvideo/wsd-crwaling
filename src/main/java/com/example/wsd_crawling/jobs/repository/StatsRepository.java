package com.example.wsd_crawling.jobs.repository;

import com.example.wsd_crawling.jobs.model.JobPostingStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface StatsRepository extends JpaRepository<JobPostingStats, Long> {

    Optional<JobPostingStats> findByJobPostingId(Long jobPostingId);

    boolean existsByJobPostingId(Long jobPostingId);

    @Modifying
    @Transactional
    @Query("UPDATE JobPostingStats j SET j.viewCount = j.viewCount + 1 WHERE j.jobPostingId = :jobPostingId")
    void incrementViewCount(@Param("jobPostingId") Long jobPostingId);
}
