package com.example.wsd_crawling.jobs.repository;

import com.example.wsd_crawling.jobs.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
    Page<Job> findByLocationContainingAndExperienceContainingAndSalaryContaining(
            String location, String experience, String salary, Pageable pageable);

    Page<Job> findByTitleContainingOrCompanyContainingOrPositionContaining(
            String title, String company, String position, Pageable pageable);
}
