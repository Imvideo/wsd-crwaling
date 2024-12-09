package com.example.wsd_crawling.jobs.controller;

import com.example.wsd_crawling.jobs.model.Job;
import com.example.wsd_crawling.jobs.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    /**
     * 공고 검색 및 필터링 API
     * @param keyword 검색 키워드 (옵션)
     * @param location 위치 필터 (옵션)
     * @param employmentType 고용 유형 필터 (옵션)
     * @param sector 분야 필터 (옵션)
     * @param minSalary 최소 급여 (기본값: 0)
     * @param maxSalary 최대 급여 (기본값: Integer.MAX_VALUE)
     * @param page 페이지 번호 (기본값: 0)
     * @param size 페이지 크기 (기본값: 20)
     * @return 필터링 및 검색된 공고 목록 페이지
     */
    @GetMapping
    public ResponseEntity<Page<Job>> getJobs(
            @RequestParam(defaultValue = "") String keyword,         // 검색 키워드
            @RequestParam(defaultValue = "") String location,        // 위치 필터
            @RequestParam(defaultValue = "") String employmentType,  // 고용 유형 필터
            @RequestParam(defaultValue = "") String sector,          // 분야 필터
            @RequestParam(defaultValue = "0") int minSalary,         // 최소 급여
            @RequestParam(defaultValue = Integer.MAX_VALUE + "") int maxSalary,   // 최대 급여
            @RequestParam(defaultValue = "0") int page,              // 페이지 번호
            @RequestParam(defaultValue = "20") int size              // 페이지 크기
    ) {
        Pageable pageable = PageRequest.of(page, size);

        // 검색 및 필터링 처리
        Page<Job> jobs = jobService.searchJobs(
                keyword, location, employmentType, sector, minSalary, maxSalary, pageable
        );

        return ResponseEntity.ok(jobs);
    }
}
