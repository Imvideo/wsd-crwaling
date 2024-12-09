package com.example.wsd_crawling.jobs.repository;

import com.example.wsd_crawling.jobs.model.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobRepository extends JpaRepository<Job, Long> {

    // 필터링 메서드: 위치, 고용 유형, 섹터, 급여
    Page<Job> findByLocationContainingAndEmploymentTypeContainingAndSectorContainingAndSalaryBetween(
            String location,
            String employmentType,
            String sector,
            Integer minSalary,
            Integer maxSalary,
            Pageable pageable
    );

    // 제목 키워드 검색과 필터링
    Page<Job> findByTitleContainingAndLocationContainingAndEmploymentTypeContainingAndSectorContainingAndSalaryBetween(
            String titleKeyword,
            String location,
            String employmentType,
            String sector,
            Integer minSalary,
            Integer maxSalary,
            Pageable pageable
    );

    // 생성일 기준으로 정렬
    Page<Job> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // 급여 범위 검색 (급여가 0이 아닌 경우만)
    @Query("SELECT j FROM Job j WHERE j.salary BETWEEN :minSalary AND :maxSalary AND j.salary > 0")
    Page<Job> findBySalaryBetweenAndExcludeZero(
            @Param("minSalary") Integer minSalary,
            @Param("maxSalary") Integer maxSalary,
            Pageable pageable
    );
}
