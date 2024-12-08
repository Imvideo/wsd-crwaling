package com.example.wsd_crawling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 최상위 패키지에서 하위 패키지의 모든 컴포넌트를 스캔
public class WsdCrawlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsdCrawlingApplication.class, args);
	}
}
