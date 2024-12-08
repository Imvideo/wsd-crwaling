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

    // 검색 및 필터링 API
    @GetMapping
    public ResponseEntity<Page<Job>> searchJobs(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "") String location,
            @RequestParam(defaultValue = "") String experience,
            @RequestParam(defaultValue = "") String salary,
            @RequestParam(defaultValue = "") String technologyStack,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Job> jobs = jobService.searchJobs(keyword, location, experience, salary, technologyStack, pageable);
        return ResponseEntity.ok(jobs);
    }
}
