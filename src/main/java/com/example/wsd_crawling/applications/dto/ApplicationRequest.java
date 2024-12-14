package com.example.wsd_crawling.applications.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "지원 요청 데이터 모델")
public class ApplicationRequest {

    @Schema(description = "사용자 ID", example = "1", required = true)
    private Long userId;

    @Schema(description = "채용 공고 ID", example = "11", required = true)
    private Long jobPostingId;

    @Schema(description = "지원 상태", example = "PENDING")
    private String status;

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getJobPostingId() {
        return jobPostingId;
    }

    public void setJobPostingId(Long jobPostingId) {
        this.jobPostingId = jobPostingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
