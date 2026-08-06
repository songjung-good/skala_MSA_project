# MSA 아키텍쳐 기반 팀 프로젝트

**회의 목적**: Agile Scrum 기반 역할 확립, LearnNexus MSA 설계 검토, Sprint 1 백로그 및 산출물 양식 확정

**팀 구성**: 총 6명 (PO 1명, SM 1명, Dev 4명)

---

## 1. 👥 역할 정의 및 담당 업무

| 역할                        | 담당자 | 전담 핵심 컴포넌트                | 주요 담당 업무                                                                                                      |
| ------------------------- | --- | ------------------------- | ------------------------------------------------------------------------------------------------------------- |
| **Product Owner (PO)**    |     | 비즈니스 백로그                  | - 비즈니스 요구사항 우선순위 결정(MoSCoW)<br>- User Story 인수기준(Acceptance Criteria) 확정<br>- Sprint 1 최종 목표 승인               |
| **Scrum Master (SM)**     |     | Scrum 프로세스                | - 타임박스 진행 및 논의 억제/분리<br>- DoD(완료 정의) 작성 주도 및 Sprint Board 셋업<br>- 회의 결과 산출물 수집 및 장애물(Impediment) 정리           |
| **Dev 1 (인증/게이트웨이)**      |     | Auth Server, Gateway      | - Spring Authorization Server (9000) 설계<br>- Spring Cloud Gateway (8080) JWT 검증 및 헤더 전파 설계                    |
| **Dev 2 (회원/강의 도메인)**     |     | User, Course Service      | - User Service (8081) 회원가입/조회 설계<br>- Course Service (8082) 강의 등록/검색/카테고리 설계                                  |
| **Dev 3 (수강/결제 & Kafka)** |     | Enrollment, Payment       | - Enrollment Service (8083) 수강신청 처리 설계<br>- Payment Service (8084) 결제 처리 및 Kafka (`payment.completed`) 이벤트 연동 |
| **Dev 4 (추천 & 인프라)**      |     | Recommend, Eureka, Docker | - Eureka Server (8761) 서비스 디스커버리 구축<br>- Recommend Service (FastAPI 8085) 규칙 기반 추천 및 Docker Compose 통합        |

---

## 2. ⏰ 진행 사항 및 산출물 양식

---

### 🟢 팀 규칙 & DoD(완료 정의) 수립

- **시간별 수행 사항**:
  - PO: 이번 회의 목표(LearnNexus MSA 구축 Sprint 1 준비) 제시
  - SM: DoD 초안 제시 및 팀원 동의 수집
  - Dev 1~4: 개발/테스트 완료 기준에 대한 기술적 의견 제시

#### 📄 [산출물 양식 1] 팀 DoD & 운영 규칙 체크리스트

```markdown
 📋 Team Definition of Done (DoD)

- 1. 기능 구현 완료 및 Unit Test / Integration Test 통과
    -2. API Gateway 및 Eureka 디스커버리 정상 등록 확인
    -3. Swagger API 명세 최신화 및 테스트 실행 확인
    -4. Git Main 브랜치 머지 전 팀원 최소 1인 이상 Code Review 완료
    -5. Docker Compose 환경에서 컨테이너 정상 기동 검증
```

---

### 🟢 MSA 도메인 설계 & 역할 매핑 검토

- **수행 사항**:
  - Dev 1: Gateway(8080) - Auth(9000) 인증 라우팅 흐름 설명
  - Dev 2: User(8081) - Course(8082) 도메인 구조 설명
  - Dev 3: Enrollment(8083) - Payment(8084) 동기 REST 요청 및 Kafka 비동기 이벤트 전달 흐름 설명
  - Dev 4: Eureka(8761) 등록 및 Recommend(8085) - MariaDB(3306) Read-Only 참조 구조 설명
  - PO: 서비스 간 통신 요구사항이 비즈니스 흐름과 일치하는지 최종 확인

#### 📄 [산출물 양식 2] 서비스 컴포넌트 및 담당자 매핑표

##### 🏗️ LearnNexus Component Mapping

| 컴포넌트명              | 포트   | 주요 기술 스택                    | 담당 개발자 | 데이터베이스 (MariaDB)          |
| ------------------ | ---- | --------------------------- | ------ | ------------------------- |
| API Gateway        | 8080 | Spring Cloud Gateway        | Dev 1  | -                         |
| Auth Server        | 9000 | Spring Authorization Server | Dev 1  | users                     |
| User Service       | 8081 | Spring Boot                 | Dev 2  | users                     |
| Course Service     | 8082 | Spring Boot                 | Dev 2  | courses                   |
| Enrollment Service | 8083 | Spring Boot + Kafka         | Dev 3  | enrollments               |
| Payment Service    | 8084 | Spring Boot + Kafka         | Dev 3  | payments                  |
| Recommend Service  | 8085 | FastAPI (Python)            | Dev 4  | enrollments, courses (RO) |
| Eureka Server      | 8761 | Spring Cloud Netflix Eureka | Dev 4  | -                         |

---

### 🟢 Sprint 1 백로그 도출 & Task 분할 / Story Point 추정

- **수행 사항**:
  - PO: Sprint 1 최우선 개발 항목(인증/라우팅 기반 구축) 백로그 제시
  - Dev 1~4: User Story를 구현 가능한 세부 Task로 분할
  - SM: Planning Poker 진행 (피보나치 수열: 1, 2, 3, 5, 8 적용하여 상대적 난이도 추정)

#### 📄 [산출물 양식 3] Sprint 1 Backlog & Task 추정표

##### 🎯 Sprint 1 Goal: 인증/게이트웨이 기반 구축 및 서비스 디스커버리 연동

| Epic / User Story     | 세부 Task                                             | 담당자   | Story Point | Status |
| --------------------- | --------------------------------------------------- | ----- | ----------- | ------ |
| **OAuth2 인증서버 구축**    | 1.1 Spring Authorization Server 프로젝트 생성             | Dev 1 | 3           | To Do  |
|                       | 1.2 JWK Set 공개키 제공 엔드포인트 구현                         | Dev 1 | 2           | To Do  |
| **API Gateway 인증 연동** | 2.1 Gateway 라우팅 규칙 (`/users/**` 등) 작성               | Dev 1 | 2           | To Do  |
|                       | 2.2 JWT 필터 및 Downstream Header 전달 구현                | Dev 1 | 3           | To Do  |
| **회원/강의 API 이관**      | 3.1 User Service 기본 CRUD 및 JWT Resource Server 설정   | Dev 2 | 3           | To Do  |
|                       | 3.2 Course Service 강의 목록/상세 API 구현                  | Dev 2 | 3           | To Do  |
| **수강/결제 비동기 기반**      | 4.1 Enrollment PENDING 상태 저장 로직 작성                  | Dev 3 | 2           | To Do  |
|                       | 4.2 Payment 완료 시 `payment.completed` Kafka 발행       | Dev 3 | 3           | To Do  |
| **인프라 & 추천 서비스**      | 5.1 Eureka Server 구축 및 서비스 등록 테스트                   | Dev 4 | 2           | To Do  |
|                       | 5.2 Recommend Service FastAPI 기본 구조 및 Dockerfile 작성 | Dev 4 | 2           | To Do  |

---

### 🟢 Daily Scrum 시뮬레이션 & 칸반 보드 구성

- **수행 사항**:
  - SM: Sprint Board (To Do / In Progress / Done) 시각화 셋업
  - Dev 1~4: 각자 맡은 첫 번째 Task를 `In Progress`로 이동 후 일일 공유 시뮬레이션 진행
    - _(어제 한 일 / 오늘 할 일 / 장애 요소 공유)_

#### 📄 [산출물 양식 4] Sprint Board 현황판 양식

#### 📌 Sprint Board (Sprint 1)

```markdown
🟩 To Do (대기 중)

- JWK Set 공개키 제공 엔드포인트 구현 (Dev 1)
  -JWT 필터 및 Downstream Header 전달 구현 (Dev 1)
  -Course Service 강의 목록/상세 API 구현 (Dev 2)
  -Payment 완료 시 `payment.completed` Kafka 발행 (Dev 3)
  -Recommend Service FastAPI 기본 구조 작성 (Dev 4)

#### 🟧 In Progress (진행 중)

- [/] Spring Authorization Server 프로젝트 생성 (Dev 1) 
-[/] User Service 기본 CRUD 및 JWT Resource Server 설정 (Dev 2) 
-[/] Enrollment PENDING 상태 저장 로직 작성 (Dev 3) 
-[/] Eureka Server 구축 및 서비스 등록 테스트 (Dev 4)

#### 🟦 Done (완료)

- LearnNexus 도메인 분리 및 데이터베이스 설계 (전원)
```

---

### 🟢 회의 마무리 & 액션 아이템 확정

- **시간별 수행 사항**:
  - PO: Sprint 1 목표 및 산출물 최종 확인
  - SM: 다음 Daily Scrum 시각(매일 09:30, 15분 타임박스) 확정 및 회의록 공유

#### 📄 [산출물 양식 5] 회의 마무리 액션 아이템 관리표

##### 🚀 Next Action Items

| 구분           | 액션 아이템                                        | 담당자   | 기한  |
| ------------ | --------------------------------------------- | ----- | --- |
| **개발 환경**    | Docker Compose 통합 설정 파일 초안 작성                 | Dev 4 |     |
| **API 명세**   | Swagger 주석 표준 및 Response DTO 공통 양식 작성         | Dev 2 |     |
| **인증 계약**    | Auth Server - Gateway 간 JWT 클레임 규격 문서화        | Dev 1 |     |
| **Scrum 운영** | Daily Scrum 및 Sprint Board 업데이트 (Jira/Notion) | SM    |     |
