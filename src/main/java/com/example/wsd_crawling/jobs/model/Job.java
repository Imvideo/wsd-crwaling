package com.example.wsd_crawling.jobs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String location;
    private String position;
    private String company;
    private String salary;

    @ElementCollection
    private List<String> techStack; // 기술 스택

    private String experience; // 경력 (e.g., Junior, Mid, Senior)
}
