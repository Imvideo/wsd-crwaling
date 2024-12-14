package com.example.wsd_crawling.bookmarks.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "즐겨찾기 요청 데이터 모델")
public class BookmarkRequest {

    @NotNull(message = "사용자 ID는 필수입니다.")
    @Schema(description = "사용자 ID", example = "1", required = true)
    private Long userId;

    @NotNull(message = "채용 공고 ID는 필수입니다.")
    @Schema(description = "채용 공고 ID", example = "13", required = true)
    private Long jobPostingId;

    // 기본 생성자
    public BookmarkRequest() {
    }

    // 매개변수 생성자
    public BookmarkRequest(Long userId, Long jobPostingId) {
        this.userId = userId;
        this.jobPostingId = jobPostingId;
    }

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
}
