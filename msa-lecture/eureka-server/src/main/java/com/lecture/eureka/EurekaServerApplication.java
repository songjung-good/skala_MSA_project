package com.lecture.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Eureka Server 애플리케이션 메인 클래스
 * - 서비스 디스커버리(Service Discovery) 역할을 수행
 * - MSA 환경 내 모든 마이크로서비스(User, Course, Enrollment, Payment, Recommend)의 동적 IP/Port 정보를 등록받아 관리
 * - @EnableEurekaServer : Spring Cloud Eureka 서버 기능을 활성화
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        // Eureka Server 애플리케이션 기동
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}

