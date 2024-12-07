package com.example.wsd_crawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.wsd_crawling.auth.repository") // 리포지토리 패키지 경로
public class WsdCrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsdCrawlingApplication.class, args);
	}
}
