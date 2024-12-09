package com.example.wsd_crawling.jobs.controller;

import com.example.wsd_crawling.jobs.model.Job;
import com.example.wsd_crawling.jobs.service.JobService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getJobDetailsWithRelatedJobs(@PathVariable Long id) {
        // 공고 상세 조회
        Job job = jobService.getJobById(id);
        if (job == null) {
            return ResponseEntity.notFound().build();
        }

        // 조회수 증가
        jobService.incrementViewCount(id);

        // 관련 공고 조회
        List<Job> relatedJobs = jobService.getRelatedJobs(id);

        // 공고 상세 정보와 관련 공고 함께 반환
        Map<String, Object> response = new HashMap<>();
        response.put("job", job);
        response.put("relatedJobs", relatedJobs);

        return ResponseEntity.ok(response);
    }
}
