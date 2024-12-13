package com.example.wsd_crawling.bookmarks.dto;

public class BookmarkRequest {

    private Long userId;
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
