package com.example.wsd_crawling.jobs.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class JobRequest {

    @Schema(description = "회사 ID", example = "1", required = true)
    @NotNull(message = "회사 ID는 필수입니다.")
    private Long companyId;

    @Schema(description = "채용 공고 제목", example = "Java", required = true)
    @NotBlank(message = "제목은 필수입니다.")
    private String title;

    @Schema(description = "채용 공고 설명", example = "Java Backend Developer를 모집합니다.", required = true)
    @NotBlank(message = "설명은 필수입니다.")
    private String description;

    @Schema(description = "위치", example = "서울", required = true)
    @NotBlank(message = "위치는 필수입니다.")
    private String location;

    @Schema(description = "연봉", example = "0", required = true)
    @NotNull(message = "연봉은 필수입니다.")
    private Integer salary;

    @Schema(description = "고용 유형", example = "정규직", required = true)
    @NotBlank(message = "고용 유형은 필수입니다.")
    private String employmentType;

    @Schema(description = "마감일", example = "2024-12-21T23:59:59", required = true)
    @NotNull(message = "마감일은 필수입니다.")
    private LocalDateTime deadline;

    @Schema(description = "직무 분야", example = "Java", required = true)
    @NotBlank(message = "직무 분야는 필수입니다.")
    private String sector;

    // Getters and Setters
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
