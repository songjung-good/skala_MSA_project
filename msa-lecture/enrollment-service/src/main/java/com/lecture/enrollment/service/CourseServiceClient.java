package com.lecture.enrollment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * Enrollment Service -> Course Service 동기 HTTP REST 연동 클라이언트
 * - Spring Cloud Eureka와 연동된 WebClient.Builder(@LoadBalanced) 사용
 * - 강의 존재 검증, 강의 상세 정보 조회, 수강생 수 증가 요청 수행
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CourseServiceClient {

    private final WebClient.Builder webClientBuilder;

    /**
     * Course Service: 강의 존재 여부 확인 (동기 REST 호출)
     * - http://course-service/api/courses/internal/exists/{id}
     *
     * @param courseId 강의 ID
     * @return 강의 존재 여부 (true/false)
     */
    public boolean existsCourse(Long courseId) {
        try {
            Boolean exists = webClientBuilder.build()
                    .get()
                    .uri("http://course-service/api/courses/internal/exists/{id}", courseId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block(); // 동기 블로킹 방식으로 결과 수신

            return Boolean.TRUE.equals(exists);
        } catch (Exception e) {
            log.error("[CourseServiceClient] 강의 존재 확인 실패 - courseId: {}, error: {}",
                    courseId, e.getMessage());
            throw new RuntimeException("Course Service 연결 실패");
        }
    }

    /**
     * Course Service: 강의 상세 정보 조회
     * - 내 수강 목록 조회 시 Course 데이터를 조합(Data Enrichment)하기 위해 사용
     *
     * @param courseId 강의 ID
     * @return 강의 데이터 Map
     */
    public Map<String, Object> getCourse(Long courseId) {
        try {
            Map<String, Object> responseBody = webClientBuilder.build()
                    .get()
                    .uri("http://course-service/api/courses/internal/{id}", courseId)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                    .block();

            if (responseBody == null) {
                throw new RuntimeException("Course Service 응답 본문이 비어 있습니다.");
            }

            log.info("[CourseServiceClient] 강의 상세 조회 성공 - courseId: {}", courseId);
            log.debug("[CourseServiceClient] 강의 상세 응답 - courseId: {}, body: {}", courseId, responseBody);

            /*
             * 응답 형태 처리:
             * 1) API Wrapper 형태로 감싸져 오는 경우 ("data": { ... })
             * 2) 객체 자체가 직접 반환되는 경우
             */
            Object data = responseBody.get("data");
            if (data instanceof Map<?, ?> dataMap) {
                @SuppressWarnings("unchecked")
                Map<String, Object> courseMap = (Map<String, Object>) dataMap;
                return courseMap;
            }

            return responseBody;
        } catch (Exception e) {
            log.error("[CourseServiceClient] 강의 상세 조회 실패 - courseId: {}, error: {}",
                    courseId, e.getMessage());
            throw new RuntimeException("Course Service 강의 상세 조회 실패");
        }
    }

    /**
     * Course Service: 수강생 수 증가 요청
     * - 수강 상태가 PENDING -> ACTIVE 로 변경 완료될 때 호출
     *
     * @param courseId 강의 ID
     */
    public void increaseEnrollmentCount(Long courseId) {
        try {
            webClientBuilder.build()
                    .post()
                    .uri("http://course-service/api/courses/internal/{id}/enrollment-count", courseId)
                    .retrieve()
                    .toBodilessEntity()
                    .block();

            log.info("[CourseServiceClient] 수강생 수 증가 완료 - courseId: {}", courseId);
        } catch (Exception e) {
            log.error("[CourseServiceClient] 수강생 수 증가 실패 - courseId: {}, error: {}",
                    courseId, e.getMessage());
        }
    }
}