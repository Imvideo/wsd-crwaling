package com.example.wsd_crawling.jobs.repository;

import com.example.wsd_crawling.jobs.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Page<Job> findByLocationContainingAndExperienceContainingAndSalaryContainingAndTechnologyStackContaining(
            String location,
            String experience,
            String salary,
            String technologyStack,
            Pageable pageable
    );

    // 키워드와 다른 조건을 결합한 검색 메서드
    Page<Job> findByTitleContainingAndLocationContainingAndExperienceContainingAndSalaryContainingAndTechnologyStackContaining(
            String title,
            String location,
            String experience,
            String salary,
            String technologyStack,
            Pageable pageable
    );

    // createdAt 기준으로 정렬
    Page<Job> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
