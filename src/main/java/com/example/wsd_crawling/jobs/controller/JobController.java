package com.example.wsd_crawling.jobs.controller;

import com.example.wsd_crawling.jobs.dto.JobRequest;
import com.example.wsd_crawling.jobs.dto.JobResponse;
import com.example.wsd_crawling.jobs.dto.JobStatsResponse;
import com.example.wsd_crawling.jobs.model.Job;
import com.example.wsd_crawling.jobs.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/jobs")
@Tag(name = "Jobs", description = "채용 공고 관련 API")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    @Operation(summary = "채용 공고 목록 조회", description = "검색 키워드 및 필터를 사용하여 채용 공고 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채용 공고 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Page<JobResponse>> getJobs(
            @RequestParam(defaultValue = "") @Schema(description = "검색 키워드", example = "Java") String keyword,
            @RequestParam(defaultValue = "") @Schema(description = "위치 필터", example = "서울") String location,
            @RequestParam(defaultValue = "") @Schema(description = "고용 유형 필터", example = "정규직") String employmentType,
            @RequestParam(defaultValue = "") @Schema(description = "분야 필터", example = "Java") String sector,
            @RequestParam(defaultValue = "0") @Schema(description = "최소 급여", example = "0") int minSalary,
            @RequestParam(defaultValue = "2147483647") @Schema(description = "최대 급여", example = "100000") int maxSalary,
            @RequestParam(defaultValue = "0") @Schema(description = "페이지 번호", example = "0") int page,
            @RequestParam(defaultValue = "20") @Schema(description = "페이지 크기", example = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<JobResponse> jobs = jobService.searchJobs(keyword, location, employmentType, sector, minSalary, maxSalary, pageable);
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "채용 공고 상세 조회", description = "특정 공고의 상세 정보와 관련 공고를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "채용 공고 상세 조회 성공",
                    content = @Content(schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "채용 공고를 찾을 수 없음", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "서버 오류", content = @Content(schema = @Schema(implementation = String.class)))
    })
    public ResponseEntity<Map<String, Object>> getJobDetailsWithRelatedJobs(
            @PathVariable @Schema(description = "공고 ID", example = "1") Long id
    ) {
        JobResponse jobResponse = jobService.getJobById(id); // DTO 반환으로 수정
        if (jobResponse == null) {
            return ResponseEntity.notFound().build();
        }

        jobService.incrementViewCount(id);

        List<JobResponse> relatedJobs = jobService.getRelatedJobs(id);

        Map<String, Object> response = new HashMap<>();
        response.put("mainJob", jobResponse); // 이미 DTO
        response.put("relatedJobs", relatedJobs);

        return ResponseEntity.ok(response);
    }

}
