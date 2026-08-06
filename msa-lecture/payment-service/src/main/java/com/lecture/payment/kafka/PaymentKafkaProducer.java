package com.lecture.payment.kafka;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * Payment Service -> Kafka 이벤트 Producer
 * - 결제 승인 완료 시 `payment.completed` 이벤트를 카프카 토픽에 발행
 * - Enrollment Service가 해당 이벤트를 소비(Consume)하여 수강 상태를 PENDING에서 ACTIVE로 변경
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.payment-completed}")
    private String paymentCompletedTopic;

    /**
     * payment.completed 이벤트 발행
     * - 메시지 Key: userId
     * - 메시지 Value: PaymentCompletedEvent
     * - 실습/검증 목적으로 10초 타임아웃 블로킹(.get())을 통해 전송 성공/실패 여부를 확정
     *
     * @param event 결제 완료 이벤트 객체 (paymentId, userId, courseId, status)
     */
    public void publishPaymentCompleted(PaymentCompletedEvent event) {
        log.info("[Kafka Producer] payment.completed 발행 시도 - topic: {}, paymentId: {}, userId: {}, courseId: {}",
                paymentCompletedTopic, event.getPaymentId(), event.getUserId(), event.getCourseId());

        try {
            // Kafka 메시지 전송 및 10초 대기(동기적 확인)
            SendResult<String, Object> result = kafkaTemplate
                    .send(paymentCompletedTopic, String.valueOf(event.getUserId()), event)
                    .get(10, TimeUnit.SECONDS);

            log.info("[Kafka Producer] payment.completed 발행 성공 - topic: {}, partition: {}, offset: {}",
                    paymentCompletedTopic,
                    result.getRecordMetadata().partition(),
                    result.getRecordMetadata().offset());

        } catch (Exception e) {
            log.error("[Kafka Producer] payment.completed 발행 실패 - topic: {}, paymentId: {}, userId: {}, courseId: {}, error: {}",
                    paymentCompletedTopic,
                    event.getPaymentId(),
                    event.getUserId(),
                    event.getCourseId(),
                    e.getMessage(),
                    e);

            throw new RuntimeException("payment.completed Kafka 발행 실패", e);
        }
    }

    /**
     * Kafka 전송용 결제 완료 이벤트 DTO
     */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PaymentCompletedEvent {
        private Long paymentId;
        private Long userId;
        private Long courseId;
        private String status;
    }
}