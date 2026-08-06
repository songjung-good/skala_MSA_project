# Agile 방법론 및 MSA 개발 - 목차별 수행 과제 (Task Checklist)

본 문서는 `/Users/skala_yh/Documents/GitHub/skala_MSA_project/Agile 방법론 및 MSA 개발.pdf` 교재의 목차 구조를 바탕으로 각 장별/항목별로 수행해야 하는 실무 과제 및 Action Item을 체계적으로 정리한 마크다운 파일입니다.

---

## 1. Agile 방법론 및 MSA 개발 (Day 1)

### 1.1 Agile 개요
* [ ] **Agile 도입 필요성 및 배경 분석**
  * 시장 및 고객 요구사항의 신속한 변화에 대응하기 위한 요구사항 가변성 점검
  * Waterfall 대비 후반 단계 결함 발견 및 수정 비용 절감 방안 수립
  * 짧은 주기(Sprint) 기반 제품 조기 검증 루프 설계
* [ ] **Agile 도입 5단계 로드맵 실행**
  1. **현황 진단**: 기존 프로세스 및 조직 문화 점검, 병목 구간 파악
  2. **파일럿팀 선정**: 자발적 참여 의사가 있는 1~2개 소규모 팀 구성
  3. **Sprint 0 준비**: 백로그 초안 작성, Definition of Done(DoD) 합의, 역할 지정
  4. **첫 Sprint 실행**: 1~2주 단기 스프린트 수행 및 Retrospective로 즉시 보정
  5. **확산 & 정착**: 성공 사례 공유 후 타 팀으로 점진적 확산
* [ ] **실제 적용 시 흔한 문제 상황 및 대응 방안 정립**
  * **무늬만 Agile**: 형식적 행사가 아닌 목적에 맞는 이벤트 및 산출물 실제 운영
  * **역할 겸직 병목**: PO 전담 배치 또는 일부 의사결정 권한의 팀 위임
  * **회고 없는 반복**: Retrospective 도출 액션 아이템을 차기 Sprint에 반드시 반영
  * **과도한 문서화 병행**: 대화 및 시각화(Board) 중심 프로세스로 전환
  * **진행상황 미가시화**: Sprint Board 및 Burndown Chart를 통한 일일 상태 시각화
* [ ] **Agile 전환 체크리스트 점검**
  * **Sprint 0 이전**: PO/SM/Dev 역할 확정, DoD 초안 합의, Product Backlog 최소 10~15개 확보
  * **Sprint 0**: Sprint 목표(Goal) 한 문장 정의, Sprint Backlog(Task 단위) 구성, Sprint Board 셋업
  * **첫 Sprint 종료 후**: Retrospective 실행 및 액션 아이템 도출, Velocity 기록 시작, 개선사항 다음 Sprint 반영

---

### 1.2 Scrum 핵심 요소
* [ ] **Scrum 3대 역할 (Role) 정의 및 담당자 배정**
  * **Product Owner (PO)**: 제품 백로그 관리, 우선순위 결정, ROI 책임
  * **Scrum Master (SM)**: 프로세스 진행 촉진, 장애물 제거, 팀 보호 및 타임박스 준수
  * **Development Team**: 기능 설계·구현·테스트, Increment(완성된 산출물) 완성
* [ ] **Scrum 4대 이벤트 타임박스(Timebox) 및 실행 프로세스 수립**
  * **Sprint Planning (2시간/2주 기준)**: Sprint 목표 합의 → Backlog 선택 → Task 분할
  * **Daily Scrum (15분 매일)**: Stand-Up 방식으로 어제 한 일 / 오늘 할 일 / 장애물 공유
  * **Sprint Review (1시간)**: 동작하는 결과물 데모 시연 및 이해관계자 피드백 수집
  * **Retrospective (45분)**: Keep / Problem / Try 프레임워크 기반 회고
* [ ] **Scrum 3대 산출물 (Artifacts) 및 완료 기준(DoD) 정립**
  * **Product Backlog**: 우선순위화된 사용자 요구사항 목록 정제(Refinement)
  * **Sprint Backlog**: 이번 Sprint 내 완료할 항목 및 세부 Task 계획
  * **Increment**: 완료의 정의(Definition of Done)를 충족하는 작동 가능한 제품 증분
  * **Definition of Done (DoD)**: 산출물이 '완료'로 인정받기 위한 팀 공통 기술/품질 기준 합의
* [ ] **Agile Delivery 공정 (Sprint #0) 운영**
  * SI 프로젝트 특성에 맞게 착수/준비 단계(Initiating & Planning)를 위한 Sprint #0 공정 적용
  * Scrum 팀 및 환경 구성, Product Backlog 도출, Release Planning 및 일감 크기 추정 수행

---

### 1.3 User Story & 백로그
* [ ] **User Story 작성 및 템플릿 적용**
  * 표준 템플릿 작성: `As a [사용자 유형], I want [원하는 기능/행동], So that [얻고자 하는 가치/이유]`
  * 비즈니스 요구 → Epic → User Story → Task의 단계적 구체화 흐름 구성
* [ ] **INVEST 원칙 준수 검증**
  * **Independent**: 다른 스토리와 독립적으로 개발 가능한가
  * **Negotiable**: 세부 구현이 협의 가능한가
  * **Valuable**: 사용자/고객에게 명확한 가치를 제공하는가
  * **Estimable**: 팀이 규모를 추정할 수 있는가
  * **Small**: 한 Sprint 내 완료 가능한 크기인가
  * **Testable**: 완료 여부를 검증할 테스트 기준이 존재하는가
* [ ] **백로그 그루밍 (Refinement) 실전 프로세스 진행**
  * 주기: Sprint 중반 1회 (1시간 이내), PO 필수 참석
  * 진행 순서: 우선순위 재정렬 → 상위 항목 상세화 → INVEST 체크 → Planning Poker 기반 Story Point 추정
* [ ] **인수 기준 (Acceptance Criteria) 명확화**
  * User Story별 완료 판단 검증 기준(Given, When, Then 형식) 상세 작성
* [ ] **MoSCoW 기법을 통한 우선순위 태깅 및 Release Planning**
  * Must Have / Should Have / Could Have / Won't Have 태깅
  * Release Roadmap 수립 및 Sprint 주기/회차 정의 (Release 1.0, 2.0 등 계획 작성)

---

### 1.4 모의 프로젝트 소개 & 가이드 & 설계 실습
* [ ] **모의 프로젝트 전체 시스템 아키텍처 이해**
  * Client → API Gateway → 인증서버(Auth) / Eureka(Discovery) / 비즈니스 서비스 → Kafka 연동 흐름 확인
* [ ] **팀 구성 및 역할 배분**
  * PO 1명 (우선순위 결정 및 인수기준 확정)
  * SM 1명 (타임박스 관리 및 장애물 정리)
  * Development Team (Task 수행, 상대적 추정 참여, 상호 리뷰)
* [ ] **컴포넌트별 샘플 User Story 도출 (최소 2개 이상/컴포넌트)**
  * 인증 관련 Story / Gateway 관련 Story / Kafka 관련 Story 작성
* [ ] **Sprint Planning 설계 실습 수행**
  * **목표 합의 (20분)**: PO가 제안한 Sprint Goal 한 문장 합의
  * **Backlog 선택 (30분)**: 우선순위 상위 User Story 수용
  * **Task 분할 (50분)**: Story를 구현 가능한 Task 단위로 분할
  * **추정 & 배분 (20분)**: Task별 담당자 및 Story Point (피보나치 수열) 배분
* [ ] **Sprint Board 운영 및 Daily Scrum 시뮬레이션**
  * Kanban Board (To Do / In Progress / Done) 세팅 및 일일 이슈 시뮬레이션 데모

---

## 2. MSA 개발, Sprint Backlog 구현 (Day 2)

### 2.1 MSA 개요 및 SOLID 리팩토링
* [ ] **모놀리식 vs MSA 차이 및 서비스 분리 기준 정립**
  * Bounded Context, 데이터 소유권, 변경 빈도 기준 서비스 분리
  * Cloud Application 전환 유형 비교 (Cloud Ready, Cloud Friendly, Cloud Native)
  * 12 Factor App 원칙 점검 및 적용
* [ ] **Pure Java 모놀리식 코드의 SOLID 원칙 리팩토링**
  * **SRP (단일 책임 원칙)**: `LoginService` 단일 클래스를 `CredentialValidator`, `SessionManager`, `AuthLogger`로 책임 분리
  * **OCP (개방-폐쇄 원칙)**: 인증 방식(비밀번호/OAuth) 추가 시 기존 코드 수정 없는 확장 구조 설계
  * **DIP (의존성 역전 원칙)**: `UserRepository` 인터페이스 의존 및 구현체 주입
* [ ] **리팩토링 컴포넌트의 MSA 이관 매핑**
  * `CredentialValidator` → 인증서버(Auth Server) 내부 로직
  * `SessionManager` → OAuth 토큰 발급 모듈
  * `LoginController` → API Gateway 진입점 + 인증서버 REST API
  * `AuthLogger` → Kafka 비동기 이벤트 전달 로직

---

### 2.2 인증서버 & API Gateway 구축 (Sprint 1)
* [ ] **Sprint 1 Planning & Task 분할**
  1. 기존 로그인 로직 분석 (3 Point)
  2. SOLID 리팩토링 (5 Point)
  3. Spring Boot 프로젝트 구조화 (3 Point)
  4. OAuth 2.0 Authorization Server 설정 및 토큰 발급 엔드포인트 구현 (5 Point)
  5. Gateway 라우팅 설정 및 인증 필터 구성 (3 Point)
* [ ] **OAuth 2.0 & JWT 인증 흐름 데모 시연**
  * Postman/프론트엔드로 `/api/login` 요청 전송
  * Gateway 라우팅 → 인증서버의 JWT 발급 → Gateway 필터 토큰 검증 시연

---

### 2.3 서비스 디스커버리 (Eureka) & 서비스 간 통신 (Sprint 2)
* [ ] **Sprint 2 Planning & Task 분할**
  1. Eureka Server 구축 (3 Point)
  2. 각 서비스(인증서버, Gateway, 수강신청)에 Eureka Client (`@EnableEurekaClient`) 등록 (3 Point)
  3. Gateway 동적 라우팅 설정 (`lb://AUTH-SERVICE` 등) (3 Point)
  4. 수강신청 서비스 ↔ 인증서버 간 REST 클라이언트 호출 구현 (5 Point)
* [ ] **동적 서비스 탐색 및 연동 데모 시연**
  * Eureka Dashboard를 통한 등록 서비스 확인
  * 수강신청 서비스가 Eureka에서 인증서버 IP:Port를 조회 후 REST 호출하는 흐름 검증

---

### 2.4 Kafka 기반 이벤트 통신 (Sprint 3)
* [ ] **Sprint 3 Planning & Task 분할**
  1. Kafka 브로커 기동 및 `course-applied` Topic 생성 (2 Point)
  2. 수강신청 서비스 Producer 구현 (`KafkaTemplate.send()`) (3 Point)
  3. 알림/로그 서비스 Consumer 구현 (`@KafkaListener`) (3 Point)
  4. End-to-End 통합 테스트 (3 Point)
* [ ] **서비스 간 통신 및 데이터 처리 고급 패턴 파악**
  * **Circuit Breaker**: Hystrix/Resilience4j를 활용한 장애 전파 차단 및 Fallback 함수 구현
  * **Anti-Corruption Layer (ACL)**: 서비스 간 도메인 모델 직접 공유 방지 및 Adaptor 레이어 가공
  * **MSA Data 처리 주의점**: WAS 단 분리 처리 시 Consistent Mode 위반 방지 및 조인/ERD 기반 설계 필요성 준수

---

### 2.5 Sprint 실행, Task 구현 및 통합 데모
* [ ] **Sprint Board 실시간 상태 업데이트 (To Do / In Progress / Done)**
* [ ] **팀별 통합 데모 시연 5단계 진행**
  1. **로그인 데모 (3분)**: OAuth 인증서버 기반 로그인 및 JWT 확인
  2. **서비스 탐색 데모 (2분)**: Eureka Dashboard 등록 상태 확인
  3. **수강신청 데모 (3분)**: 토큰 포함 수강신청 REST 요청 실행
  4. **이벤트 처리 데모 (2분)**: Kafka Topic 발행 및 알림 서비스 콘솔 로그 출력 시연
  5. **Q&A (2분)**: 구현 중 이슈 및 해결 과정 공유

---

### 2.6 Sprint Review & Retrospective
* [ ] **Sprint Review 개최**
  * 데모 시연 (10분) → 산출물 리뷰 (5분) → 이해관계자 피드백 (5분)
* [ ] **Day 1 계획 vs Day 2 실행 갭 분석**
  * 계획보다 시간이 소요된 Task 원인 분석 (추정 오류 vs 기술적 난이도)
* [ ] **Retrospective (회고) 실행**
  * **Keep**: 계속 유지할 잘된 점 (Sprint 단위 분할, Daily Scrum 등)
  * **Problem**: 발생한 문제점 (Story Point 추정 오차, 설정 문서 탐색 소요 등)
  * **Try**: 차기 시도할 개선 액션 아이템 (Spike Task 배정, 보수적 Story Point 추정 등)

---

## 3. MSA를 이용한 웹 서비스 구축 (온라인 교육 플랫폼 구축 실습)

### 3.1 Step 1. 요구사항 분석
* [ ] **주체별 핵심 기능 요구사항 도출**
  * **수강생**: 회원가입, 로그인, 강의 검색, 수강신청, 결제, 추천 강의 조회
  * **강사**: 회원가입, 로그인, 강의 등록
  * **시스템**: 결제 완료 후 수강 활성화, 수강 완료 후 추천 갱신

---

### 3.2 Step 2. 도메인 분리 & 서비스 정의
* [ ] **5개 독립 Microservice 경계 및 기술 스택/포트/데이터 설계**
  * **User Service** (Spring Boot, Port 8081): 회원가입, 로그인, JWT 인증 / `users` DB
  * **Course Service** (Spring Boot, Port 8082): 강의 등록, 목록, 검색, 카테고리 관리 / `courses` DB
  * **Enrollment Service** (Spring Boot, Port 8083): 수강신청, 수강 상태 관리 / `enrollments` DB
  * **Payment Service** (Spring Boot, Port 8084): 결제 처리, 결제 내역 / `payments` DB
  * **Recommend Service** (FastAPI, Port 8085): 수강 이력 기반 규칙 추천 / `enrollments + courses` 연동

---

### 3.3 Step 3. 인프라 구성 설계
* [ ] **인프라 요소 및 역할 정의**
  * **API Gateway Layer**: Spring Cloud Gateway (Port 8080) - 단일 진입점 및 라우팅
  * **Service Discovery**: Eureka Server (Port 8761) - 서비스 위치 자동 등록/탐색
  * **Async Message Bus**: Kafka Broker (KRaft 모드, Port 9092) - 비동기 이벤트 전달
  * **Database**: MariaDB - 독립/단일 데이터베이스 관리
  * **오케스트레이션**: Docker Compose 기반 컨테이너 구성 및 묶음 실행 환경 구성

---

### 3.4 Step 4. 서비스간 통신 설계 (동기/비동기)
* [ ] **전체 통신 시퀀스 워크플로우 구축**
  1. Client → Gateway (`POST /enrollments` 요청)
  2. Gateway → Enrollment Service (라우팅)
  3. Enrollment Service → Course Service (동기 REST: 강의 존재 확인)
  4. Enrollment Service → Payment Service (동기 REST: 결제 요청)
  5. Payment Service → Kafka (`payment.completed` 비동기 이벤트 발행)
  6. Enrollment Service (이벤트 수신 후 수강 상태 `ACTIVE` 변경)
  7. Recommend Service (Kafka 이벤트 구독 / 추천 갱신 트리거 및 REST로 수강이력 조회)

---

### 3.5 Step 5~7. DB/ERD, API 명세, 컨테이너 구성 및 통합 배포
* [ ] **Database 모델링 및 ERD 작성**
  * `users`, `courses`, `enrollments`, `payments` 데이터베이스 개체 및 필드 명세 작성
* [ ] **API 명세 설계 및 Swagger 문서화**
  * Open API 3.0 명세서 작성 및 `/api-docs` 엔드포인트 검증
* [ ] **Frontend(LearnNexus UI) - Backend - Infra 전체 통합 구축**
  * React/Vue 기반 UI 웹 애플리케이션 프론트엔드 연동
  * Docker Compose 전체 프로젝트 패키징 및 로컬/클라우드 배포 실행 검증
