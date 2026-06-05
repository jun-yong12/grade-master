# 🎓 학생 성적 관리 시스템

한국공학대학교 SW프레임워크 강의 스타일로 만든 **Spring Boot 학생 성적 관리 프로젝트**입니다.

## 기술 스택

| 구분 | 기술 |
|------|------|
| 백엔드 | Spring Boot 3.5.x, Java 21 |
| 뷰 엔진 | Thymeleaf |
| ORM | **MyBatis** |
| 데이터베이스 | MySQL 8.x (운영), H2 (개발) |
| 빌드 도구 | Gradle (Wrapper) |
| 보안 | spring-security-crypto (BCrypt) |
| API 문서 | springdoc-openapi 2.7.0 (Swagger UI) |
| 테스트 커버리지 | JaCoCo |

## 주요 기능

- 학생 CRUD (등록/조회/수정/삭제)
- 이름·학번 검색, 페이징, 정렬
- 세션 기반 로그인/로그아웃 (BCrypt 비밀번호)
- 인터셉터 접근 제어
- AOP 메서드 실행 시간 측정
- 성적 입력/조회/삭제, 평균·등급·등수 계산
- 성적표 조회 및 인쇄
- 다국어(i18n) 한국어/영어 전환 (`?lang=ko` / `?lang=en`)
- Swagger UI API 문서 자동 생성

## 실행 방법

### H2 인메모리 DB로 실행 (MySQL 없이 — 바로 실행 가능)
```bash
./gradlew bootRun --args='--spring.profiles.active=h2'
```

### MySQL로 실행 (기본)
```bash
# MySQL에서 DB 생성
mysql -u root -p -e "CREATE DATABASE gradedb CHARACTER SET utf8mb4;"

# application.yml에서 비밀번호 수정 후
./gradlew bootRun
```

### 브라우저 접속
```
http://localhost:8080          # 메인 (자동 로그인 화면으로 이동)
http://localhost:8080/swagger-ui/index.html  # Swagger UI
http://localhost:8080/h2-console             # H2 콘솔 (h2 프로파일만)
```

### 테스트 계정
| 아이디 | 비밀번호 | 권한 |
|--------|----------|------|
| admin  | 1234     | 관리자 |
| guest  | 1234     | 게스트 |

## 프로젝트 구조

```
src/main/java/kr/ac/tukorea/grade/
├── GradeManagementApplication.java
├── config/
│   ├── AppConfig.java          # @Bean 수동 등록
│   └── WebConfig.java          # 인터셉터, i18n 설정
├── controller/
│   ├── HomeController.java
│   ├── LoginController.java    # 세션 로그인/로그아웃
│   ├── StudentController.java  # 학생 CRUD + 성적표
│   └── GradeController.java    # 성적 등록/삭제
├── service/
│   ├── StudentService.java
│   ├── StudentServiceImpl.java
│   ├── GradeService.java
│   └── GradeServiceImpl.java
├── mapper/
│   ├── StudentMapper.java      # MyBatis Mapper
│   └── GradeMapper.java
├── repository/
│   └── UserRepository.java     # 인메모리 사용자 저장소
├── dto/
│   ├── StudentDTO.java
│   ├── GradeDTO.java
│   ├── ReportCardDTO.java
│   ├── PageDTO.java
│   └── LoginForm.java
├── interceptor/
│   └── LoginInterceptor.java   # 로그인 체크
├── aspect/
│   └── ExecutionTimeAspect.java # AOP 실행시간 측정
└── util/
    └── PasswordUtil.java        # BCrypt 래퍼
```

## IntelliJ 실행

1. `File > Open > grade-management 폴더`
2. Gradle 팝업 → **Load Gradle Changes**
3. `File > Project Structure > SDK` → **Java 21** 선택
4. `GradeManagementApplication.java` → ▶ 실행
5. `http://localhost:8080` 접속
