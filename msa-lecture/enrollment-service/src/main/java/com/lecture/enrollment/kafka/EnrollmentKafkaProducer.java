package com.lecture.enrollment.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Enrollment Service -> Kafka 이벤트 Producer
 * - 수강 활성화(PENDING -> ACTIVE) 완료 시 `enrollment.completed` 이벤트를 토픽에 발행
 * - Recommend Service 등이 메시지를 소비하여 비동기로 추천 갱신 또는 로그를 기록함
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class EnrollmentKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.enrollment-completed}")
    private String enrollmentCompletedTopic;

    /**
     * enrollment.completed 이벤트 토픽 발행
     * - Key: userId (동일 사용자의 이벤트를 동일 파티션에 할당하기 위함)
     * - Value: EnrollmentCompletedEvent DTO
     *
     * @param event 수강 완료 이벤트 객체 (enrollmentId, userId, courseId)
     */
    public void publishEnrollmentCompleted(KafkaEvent.EnrollmentCompletedEvent event) {
        log.info("[Kafka Producer] enrollment.completed 발행 - enrollmentId: {}, userId: {}, courseId: {}",
                event.getEnrollmentId(), event.getUserId(), event.getCourseId());

        // Kafka 메시지 비동기 전송
        kafkaTemplate.send(enrollmentCompletedTopic, String.valueOf(event.getUserId()), event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("[Kafka Producer] enrollment.completed 발행 실패: {}", ex.getMessage());
                    } else {
                        log.info("[Kafka Producer] enrollment.completed 발행 성공 - offset: {}",
                                result.getRecordMetadata().offset());
                    }
                });
    }
}

