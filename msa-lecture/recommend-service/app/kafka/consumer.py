import json
import logging
import threading
from kafka import KafkaConsumer
from app.config.settings import settings

logger = logging.getLogger(__name__)


class EnrollmentCompletedConsumer:
    """
    Kafka Consumer: enrollment.completed 이벤트 수신 클래스
    - Enrollment Service에서 수강 활성화(ACTIVE) 처리 완료 시 발행하는 Kafka 이벤트를 감지
    - 이벤트 기반으로 추천 시스템 데이터 갱신 및 캐시 무효화 등의 작업을 비동기로 처리
    """

    def __init__(self):
        self.topic = settings.kafka_topic_enrollment_completed
        self.consumer = None
        self._running = False

    def start(self):
        """
        메인 스레드를 블로킹하지 않도록 데몬 데몬 스레드(Daemon Thread)에서 Kafka Consumer를 비동기로 시작
        """
        self._running = True
        thread = threading.Thread(target=self._consume, daemon=True)
        thread.start()
        logger.info(f"[KafkaConsumer] 시작 - topic: {self.topic}")

    def stop(self):
        """Kafka Consumer 안전 종료"""
        self._running = False
        if self.consumer:
            self.consumer.close()

    def _consume(self):
        """
        Kafka Consumer 루프 실행
        - bootstrap_servers: Kafka 브로커 주소 (kafka:9092)
        - group_id: Recommend Service 전용 컨슈머 그룹
        - value_deserializer: JSON 역직렬화
        """
        try:
            self.consumer = KafkaConsumer(
                self.topic,
                bootstrap_servers=settings.kafka_bootstrap_servers,
                group_id=settings.kafka_consumer_group_id,
                auto_offset_reset="earliest",
                enable_auto_commit=True,
                value_deserializer=lambda m: json.loads(m.decode("utf-8")),
                consumer_timeout_ms=1000,
            )

            # 앱이 실행 중인 동안 메시지 폴링 반복
            while self._running:
                for message in self.consumer:
                    if not self._running:
                        break
                    self._handle_message(message.value)

        except Exception as e:
            logger.error(f"[KafkaConsumer] 오류 발생: {e}")
        finally:
            if self.consumer:
                self.consumer.close()

    def _handle_message(self, event: dict):
        """
        enrollment.completed 이벤트 메시지 핸들러
        - 수강 완료 정보(enrollmentId, userId, courseId) 파싱 및 로그 기록
        - 추천 캐시 무효화 또는 모델 업데이트 수행 포인트
        """
        try:
            enrollment_id = event.get("enrollmentId")
            user_id = event.get("userId")
            course_id = event.get("courseId")

            logger.info(
                f"[KafkaConsumer] enrollment.completed 수신 - "
                f"enrollmentId: {enrollment_id}, userId: {user_id}, courseId: {course_id}"
            )

            # 추가 확장 포인트: 수강 완료 사용자의 추천 캐시 무효화 처리 가능

        except Exception as e:
            logger.error(f"[KafkaConsumer] 메시지 처리 실패: {e}, event: {event}")


# 싱글톤 인스턴스 생성
enrollment_consumer = EnrollmentCompletedConsumer()

