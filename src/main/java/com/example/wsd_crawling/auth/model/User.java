package com.example.wsd_crawling.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // 데이터베이스 테이블명과 매핑
public class User {

    @Id
    private Long id; // 기본 키로 사용할 필드

    private String username; // 사용자명
    private String password; // 비밀번호

    // 기본 생성자
    public User() {}

    // getter, setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
