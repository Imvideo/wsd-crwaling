package com.example.wsd_crawling.jobs.model;

import jakarta.persistence.*;

@Entity
@Table(name = "job_posting_stats")
public class JobPostingStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_posting_id", nullable = false)
    private Long jobPostingId;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "apply_count", nullable = false)
    private int applyCount = 0;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
