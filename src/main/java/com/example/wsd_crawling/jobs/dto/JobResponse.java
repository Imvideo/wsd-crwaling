package com.example.wsd_crawling.jobs.dto;

import com.example.wsd_crawling.jobs.model.Job;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class JobResponse {

    @Schema(description = "채용 공고 ID", example = "1")
    private Long id;

    @Schema(description = "회사 ID", example = "1")
    private Long companyId;

    @Schema(description = "채용 공고 제목", example = "Java")
    private String title;

    @Schema(description = "채용 공고 설명", example = "Java Backend Developer를 모집합니다.")
    private String description;

    @Schema(description = "위치", example = "서울")
    private String location;

    @Schema(description = "연봉", example = "0")
    private Integer salary;

    @Schema(description = "고용 유형", example = "정규직")
    private String employmentType;

    @Schema(description = "생성일", example = "2024-11-19T00:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "수정일", example = "2024-12-02T14:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "마감일", example = "2024-12-25T00:00:00")
    private LocalDateTime deadline;

    @Schema(description = "직무 분야", example = "Java")
    private String sector;

    public JobResponse(Job job) {
        this.id = job.getId();
        this.title = job.getTitle();
        this.description = job.getDescription();
        this.location = job.getLocation();
        this.salary = job.getSalary();
        this.employmentType = job.getEmploymentType();
        this.sector = job.getSector();
        this.deadline = job.getDeadline();
        this.createdAt = job.getCreatedAt();
        this.companyId = job.getCompanyId();
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }
}
