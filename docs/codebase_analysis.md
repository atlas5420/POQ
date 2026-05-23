# POQ 프로젝트 설계 문서 기반 소스코드 분석 보고서

본 문서는 기획서, 아키텍처 설계서, DB 정의서, 요구사항 정의서 4종의 설계 문서를 분석하고, 현재 POQ 백엔드 소스코드와의 차이점(Gap)을 규명하여 리팩토링 로드맵을 정리한 보고서입니다.

---

## 1. 현재 소스코드 vs 설계 명세 주요 차이점 (Gap Analysis)

| 구분 | 현재 소스코드 구현 (`src/main/java`) | 요구사항 및 DB 정의서 명세 (`.pdf`, `.xlsx`) | 영향 및 문제점 |
| :--- | :--- | :--- | :--- |
| **PK 및 FK 식별자 타입** | `Long` (Auto-Increment) | `UUID` (Universally Unique Identifier) | 모든 엔티티의 ID 타입 및 연관관계 매핑 수정 필요 |
| **핵심 비즈니스 도메인** | `Diary`, `HealthLog`, `WalkLog` | `Question`, `DailyAssignment`, `Entry`, `MonthlyArchive` | 현재 도메인 구성은 요구사항과 불일치하며 전면 재설계 필요 (기존 건강/산책 일지 대신 질문 기반 기록 체계로 개편) |
| **회원 테이블 (`users`)** | `username`, `password`, `name`, `email` 기반 일반 회원가입 | OAuth 2.0 소셜 로그인 기반 (`email`, `provider` [email/google/apple]) 및 `nickname` | 로그인/회원가입 스키마 및 DTO 구조 변경 필요 (비밀번호 불필요) |
| **반려동물 테이블 (`pets`)** | `name`, `type`, `breed`, `birthDate` | `name`, `species` (종), `breed` (품종), `birthday`, `adoption_day` (입양일), `gender`, `photo_url`, `notes` | 필드명 변경 (`type` -> `species`, `birthDate` -> `birthday`) 및 신규 필드 추가 필요 |
| **질문 마스터 (`questions`)** | 미구현 | `id`, `category` (습관/산책/소리 등), `text`, `species_type` (대상 종), `active`, `created_at` | 질문 목록 데이터를 관리하는 질문 마스터 엔티티/Repository 신규 생성 필요 |
| **일일 질문 배정 (`daily_assignments`)** | 미구현 | `id`, `user_id`, `pet_id`, `question_id`, `assigned_date`, `status` | 매일 자정 또는 특정 시간별 질문 배정 배치 로직 및 엔티티 구현 필요 |
| **사용자 기록 (`entries`)** | `Diary` 엔티티로 간접 구현됨 | `id`, `user_id`, `pet_id`, `question_id`, `answer_text`, `photo_url`, `tags`, `entry_date`, `created_at` | 질문 기반의 기록(일기) 엔티티로 재설계 및 태그 리스트 처리 추가 필요 |
| **월간 아카이브 (`monthly_archives`)** | 미구현 | `id`, `user_id`, `pet_id`, `month`, `summary_text`, `cover_photo_url`, `generated_at` | 한 달 간의 기록을 집계 및 요약하는 배치 프로세스 및 테이블 구현 필요 |

---

## 2. 세부 도메인 모델 매핑 및 엔티티 설계 방향

설계 문서 명세를 완벽히 준수하기 위해 재설계해야 할 엔티티 스펙은 다음과 같습니다.

### 2.1. User (사용자 계정)
*   **Table**: `users`
*   **Fields**:
    *   `id`: `UUID` (PK)
    *   `email`: `VARCHAR(255)` (Unique, Not Null)
    *   `nickname`: `VARCHAR(100)` (Nullable)
    *   `provider`: `VARCHAR(50)` (Not Null, `email` / `google` / `apple`)
    *   `created_at`: `TIMESTAMP` (Not Null)

### 2.2. Pet (반려동물 프로필)
*   **Table**: `pets`
*   **Fields**:
    *   `id`: `UUID` (PK)
    *   `user`: `User` (FK, `user_id` UUID, Lazy)
    *   `name`: `VARCHAR(100)` (Not Null)
    *   `species`: `VARCHAR(50)` (Not Null, 강아지/고양이 등)
    *   `breed`: `VARCHAR(100)` (Nullable)
    *   `birthday`: `DATE` (Nullable)
    *   `adoption_day`: `DATE` (Nullable)
    *   `gender`: `VARCHAR(10)` (Nullable)
    *   `photo_url`: `VARCHAR(500)` (Nullable)
    *   `notes`: `TEXT` (Nullable, 특징 메모)
    *   `created_at`: `TIMESTAMP`

### 2.3. Question (질문 마스터)
*   **Table**: `questions`
*   **Fields**:
    *   `id`: `UUID` (PK)
    *   `category`: `VARCHAR(50)` (Not Null, 습관/산책/소리/표정·자세/관계/계절/건강관찰 등)
    *   `text`: `TEXT` (Not Null, 질문 내용)
    *   `species_type`: `VARCHAR(50)` (Nullable, 특정 종 전용 질문 필터용)
    *   `active`: `BOOLEAN` (Not Null)
    *   `created_at`: `TIMESTAMP`

### 2.4. DailyAssignment (일일 질문 배정)
*   **Table**: `daily_assignments`
*   **Fields**:
    *   `id`: `UUID` (PK)
    *   `user`: `User` (FK, `user_id` UUID)
    *   `pet`: `Pet` (FK, `pet_id` UUID)
    *   `question`: `Question` (FK, `question_id` UUID)
    *   `assigned_date`: `DATE` (Not Null, YYYY-MM-DD)
    *   `status`: `VARCHAR(20)` (Not Null, `pending` / `completed`)

### 2.5. Entry (사용자 기록)
*   **Table**: `entries`
*   **Fields**:
    *   `id`: `UUID` (PK)
    *   `user`: `User` (FK, `user_id` UUID)
    *   `pet`: `Pet` (FK, `pet_id` UUID)
    *   `question`: `Question` (FK, `question_id` UUID)
    *   `answer_text`: `TEXT` (Nullable)
    *   `photo_url`: `VARCHAR(500)` (Nullable)
    *   `tags`: `VARCHAR(255)` (Nullable, 쉼표 구분자 형식 또는 JSON)
    *   `entry_date`: `DATE` (Not Null, YYYY-MM-DD)
    *   `created_at`: `TIMESTAMP`

### 2.6. MonthlyArchive (월간 아카이브 결과)
*   **Table**: `monthly_archives`
*   **Fields**:
    *   `id`: `UUID` (PK)
    *   `user`: `User` (FK, `user_id` UUID)
    *   `pet`: `Pet` (FK, `pet_id` UUID)
    *   `month`: `VARCHAR(10)` (Not Null, YYYY-MM)
    *   `summary_text`: `TEXT` (Nullable, 대표 문장 및 요약)
    *   `cover_photo_url`: `VARCHAR(500)` (Nullable, 대표 사진 URL)
    *   `generated_at`: `TIMESTAMP` (Not Null)

---

## 3. 핵심 비즈니스 로직 및 아키텍처 흐름

### 3.1. 일일 질문 배정 (Daily Assignment) 배치
*   **목적**: 매일 자정에 사용자별 반려동물 마다 하나의 관찰 질문을 할당합니다.
*   **동작 방식**: 
    1.  스프링 `@Scheduled`를 활용한 크론 트리거 실행 (`0 0 0 * * *`).
    2.  활동 중인 모든 반려동물(`pets`) 목록을 조회합니다.
    3.  `questions` 마스터 테이블에서 반려동물의 `species`를 매칭하고, 활성화된(`active=true`) 질문 중 카테고리별로 랜덤하게 1개의 질문을 선정합니다.
    4.  선정된 질문 정보를 `daily_assignments` 테이블에 `assigned_date = 오늘날짜`, `status = pending` 상태로 적재합니다.

### 3.2. 월간 아카이브 집계 (Monthly Archive) 배치
*   **목적**: 매월 1일 새벽에 지난달의 기록을 요약하여 월간 회고 아카이브 카드를 생성합니다.
*   **동작 방식**:
    1.  매월 1일 새벽 크론 트리거 실행.
    2.  지난달(`YYYY-MM`)에 등록된 모든 `entries` 데이터를 조회합니다.
    3.  가장 많이 등장한 태그(`tags`) 통계 계산, 작성된 기록 개수 집계, 대표적인 한 줄 답변 및 사진을 요약하여 `monthly_archives`에 적재합니다.

---

## 4. 소스코드 전환(Refactoring) 로드맵 제안

기존 코드를 폐기하고 신규 요구사항 명세에 부합하도록 단계별 전환 작업을 계획할 수 있습니다.

1.  **1단계: 데이터베이스 연결 정보 동기화**
    *   `application.yml`의 DB 설정(`POQ`/`postgres`/`1234`)과 `docker-compose.yml`의 PostgreSQL 컨테이너 계정 정보(`pet_archive`/`localuser`/`localpassword`) 통합 및 불일치 해결.
2.  **2단계: 기존 엔티티 전면 리팩토링 및 추가**
    *   `id` 필드를 `Long`에서 `UUID`로 변경.
    *   기존 도메인(`diary`, `health`, `walk`) 삭제.
    *   신규 6개 도메인 엔티티 정의 (`User`, `Pet`, `Question`, `DailyAssignment`, `Entry`, `MonthlyArchive`).
3.  **3단계: 레포지토리 및 DTO 재작성**
    *   신규 엔티티에 맞는 Spring Data JPA Repository 작성.
    *   가입/등록/조회 스펙에 맞춘 Request/Response DTO 정의.
4.  **4단계: 비즈니스 서비스 및 컨트롤러 구현**
    *   소셜 로그인/게스트 처리 로직, 반려동물 등록 API 구현.
    *   질문 조회, 답변 작성, 타임라인 조회 API 구현.
5.  **5단계: 배치 로직 구현**
    *   `@Scheduled` 기반 자정 질문 배정 서비스 및 월초 아카이브 집계 기능 개발.
6.  **6단계: 테스트 코드 정비**
    *   `PoqApplicationTests.java` 패키지 오기(오류) 해결 및 비즈니스 시나리오 검증 테스트 작성.
