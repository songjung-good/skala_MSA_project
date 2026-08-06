package com.lecture.enrollment.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

/**
 * Enrollment Service -> Payment Service 동기 HTTP REST 연동 클라이언트
 * - 수강 신청 접수 후 결제 승인 요청을 전달
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentServiceClient {

    private final WebClient.Builder webClientBuilder;

    /**
     * Payment Service로 결제 승인 요청 전달 (동기 REST 호출)
     * - http://payment-service:8084/api/payments/internal/request
     *
     * @param userId   수강생 사용자 ID
     * @param courseId 신청 강의 ID
     * @param amount   결제 금액
     * @return 결제 결과 DTO (paymentId, status)
     */
    public PaymentResult requestPayment(Long userId, Long courseId, BigDecimal amount) {
        try {
            PaymentRequest request = new PaymentRequest(userId, courseId, amount);

            // WebClient로 Payment Service 호출 (동기 블로킹 방식 block() 사용)
            PaymentResult result = webClientBuilder.build()
                    .post()
                    .uri("http://payment-service:8084/api/payments/internal/request")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(PaymentResult.class)
                    .block();

            log.info("[PaymentServiceClient] 결제 요청 완료 - userId: {}, courseId: {}, result: {}",
                    userId, courseId, result != null ? result.getStatus() : "null");

            return result;
        } catch (Exception e) {
            log.error("[PaymentServiceClient] 결제 요청 실패 - userId: {}, courseId: {}, error: {}",
                    userId, courseId, e.getMessage(), e);
            throw new RuntimeException("Payment Service 연결 실패");
        }
    }

    /**
     * 내부 요청 DTO 구조체
     */
    @Getter
    @NoArgsConstructor
    static class PaymentRequest {
        private Long userId;
        private Long courseId;
        private BigDecimal amount;

        PaymentRequest(Long userId, Long courseId, BigDecimal amount) {
            this.userId = userId;
            this.courseId = courseId;
            this.amount = amount;
        }
    }

    /**
     * 내부 응답 DTO 구조체
     */
    @Getter
    @NoArgsConstructor
    public static class PaymentResult {
        private Long paymentId;
        private String status; // COMPLETED / FAILED
    }
}