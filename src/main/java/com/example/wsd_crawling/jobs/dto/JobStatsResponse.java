package com.example.wsd_crawling.jobs.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class JobStatsResponse {

    @Schema(description = "채용 공고 ID", example = "1")
    private Long jobPostingId;

    @Schema(description = "조회수", example = "0")
    private int viewCount;

    @Schema(description = "지원수", example = "0")
    private int applyCount;

    // Getters and Setters
    public Long getJobPostingId() {
        return jobPostingId;
    }

    public void setJobPostingId(Long jobPostingId) {
        this.jobPostingId = jobPostingId;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(int applyCount) {
        this.applyCount = applyCount;
    }
}
