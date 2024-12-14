package com.example.wsd_crawling.applications.controller;

import com.example.wsd_crawling.applications.dto.ApplicationRequest;
import com.example.wsd_crawling.applications.model.Application;
import com.example.wsd_crawling.applications.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    @Operation(
            summary = "지원 등록",
            description = "사용자가 특정 채용 공고에 지원합니다.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "지원 요청 데이터",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ApplicationRequest.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "지원 요청 예시",
                                    value = """
                {
                    "userId": 1,
                    "jobPostingId": 101,
                    "status": "PENDING"
                }
                """
                            )
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "지원 성공"),
            @ApiResponse(responseCode = "409", description = "이미 지원한 공고입니다.", content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    public ResponseEntity<String> apply(
            @Valid @RequestBody ApplicationRequest request) { // Spring의 @RequestBody 사용
        try {
            applicationService.apply(request.getUserId(), request.getJobPostingId(), request.getStatus());
            return ResponseEntity.ok("지원이 성공적으로 제출되었습니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }


    @GetMapping
    @Operation(summary = "지원 내역 조회", description = "사용자의 지원 내역을 조회합니다. 상태별 필터링과 페이지네이션을 지원합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content)
    })
    public ResponseEntity<Page<Application>> getApplications(
            @RequestParam @Schema(description = "사용자 ID", example = "1") Long userId,
            @RequestParam(required = false) @Schema(description = "지원 상태", example = "PENDING") String status,
            @RequestParam(defaultValue = "0") @Schema(description = "페이지 번호", example = "0") int page,
            @RequestParam(defaultValue = "10") @Schema(description = "페이지 크기", example = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Application> applications = applicationService.getApplicationsByUser(userId, status, pageable);
        return ResponseEntity.ok(applications);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "지원 취소", description = "사용자의 Pending 상태의 지원을 취소합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "취소 성공", content = @Content),
            @ApiResponse(responseCode = "409", description = "취소 가능한 Pending 상태의 지원이 없습니다.", content = @Content)
    })
    public ResponseEntity<String> deletePendingApplications(
            @PathVariable @Schema(description = "사용자 ID", example = "1") Long userId) {
        boolean isDeleted = applicationService.deletePendingApplicationsByUserId(userId);
        if (isDeleted) {
            return ResponseEntity.ok("Pending 상태의 지원이 성공적으로 삭제되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("취소 가능한 Pending 상태의 지원이 없습니다.");
        }
    }
}
