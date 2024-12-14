# WSD Crawling Application

Spring Boot를 기반으로 한 채용 공고 지원 및 북마크 관리 애플리케이션입니다.

---

## 사전 준비
애플리케이션 실행 전에 아래의 환경이 구성되어야 합니다:
- Ubuntu 서버
- OpenJDK 17
- MySQL 서버

---

## 설치 가이드

### 1. 시스템 업데이트
다음 명령어를 실행하여 시스템을 최신 상태로 유지하세요:
```bash
sudo apt update
```

### 2. OpenJDK 17 설치
Spring Boot 애플리케이션 실행을 위해 OpenJDK 17을 설치합니다:

```bash
sudo apt install openjdk-17-jdk -y
```

### 3. MySQL 서버 설치
MySQL 서버를 설치하려면 아래 명령어를 실행하세요:
```bash
sudo apt update
sudo apt install mysql-server -y
```

### 4. MySQL 서비스 시작 및 자동 시작 설정
MySQL 서비스를 시작하고, 부팅 시 자동으로 시작되도록 설정합니다:
```bash
sudo systemctl start mysql
sudo systemctl enable mysql
```

## 애플리케이션 설정

### 1. jar 파일로 빌드
```bash
./gradlew clean build 
```

### 2. scp로 파일 복사
```bash
scp -i [pem Key] -P [19xxx] saramin.sql ubuntu@113.198.66.75:/home/ubuntu/[.jar 파일]
```

### 3. 애플리케이션 실행
```bash
java -jar target/wsd-crawling-0.0.1-SNAPSHOT.jar
```

### Swagger 접속 및 테스트
http://113.198.66.75:17149/swagger-ui/index.html#/