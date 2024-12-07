package com.example.wsd_crawling.auth.model;

public class UserRegistrationRequest {

    private String email;
    private String password;
    private String name;

    // 기본 생성자
    public UserRegistrationRequest() {
    }

    // 모든 필드를 포함하는 생성자
    public UserRegistrationRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    // Getter 및 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
