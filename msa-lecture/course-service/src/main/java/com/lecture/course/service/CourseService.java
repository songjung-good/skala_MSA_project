package com.lecture.course.service;

import com.lecture.course.dto.CourseDto;
import com.lecture.course.entity.Course;
import com.lecture.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 강의 관련 비즈니스 로직 서비스
 * - 강의 등록, 조회, 수강생 수 증가, 추천용 강의 조회 기능 제공
 * - 기본적으로 읽기 전용 트랜잭션(@Transactional(readOnly = true)) 적용
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;

    /**
     * 신규 강의 등록 (강사 전용)
     *
     * @param request      강의 생성 요청 DTO (제목, 설명, 카테고리, 가격)
     * @param instructorId Gateway 헤더(X-User-Id)에서 전달받은 강사 사용자 ID
     * @return 생성된 강의 응답 DTO
     */
    @Transactional
    public CourseDto.CourseResponse createCourse(CourseDto.CreateRequest request, Long instructorId) {
        // 1. Course 엔티티 생성 (기본 상태: ACTIVE, 수강생 수: 0)
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .instructorId(instructorId)
                .build();

        // 2. DB 저장 및 DTO 변환 반환
        return CourseDto.CourseResponse.from(courseRepository.save(course));
    }

    /**
     * 강의 단건 상세 조회
     *
     * @param id 강의 식별자 (PK)
     * @return 강의 응답 DTO
     */
    public CourseDto.CourseResponse getCourse(Long id) {
        Course course = findCourseById(id);
        return CourseDto.CourseResponse.from(course);
    }

    /**
     * 전체 활성화(ACTIVE) 강의 목록 조회
     *
     * @return 활성 강의 DTO 리스트
     */
    public List<CourseDto.CourseResponse> getAllCourses() {
        return courseRepository.findByStatus(Course.Status.ACTIVE).stream()
                .map(CourseDto.CourseResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 카테고리별 활성화 강의 목록 조회
     *
     * @param category 강의 카테고리 (BACKEND, FRONTEND, DEVOPS 등)
     * @return 해당 카테고리의 활성 강의 DTO 리스트
     */
    public List<CourseDto.CourseResponse> getCoursesByCategory(Course.Category category) {
        return courseRepository.findByCategoryAndStatus(category, Course.Status.ACTIVE).stream()
                .map(CourseDto.CourseResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 강의 존재 여부 확인 (Enrollment Service에서 수강신청 전 검증용 REST API)
     *
     * @param id 강의 ID
     * @return 존재 여부 boolean
     */
    public boolean existsCourse(Long id) {
        return courseRepository.existsById(id);
    }

    /**
     * 수강생 수 증가 처리 (Enrollment Service에서 Kafka 이벤트 처리 및 수강 활성화 완료 시 REST 호출)
     *
     * @param courseId 대상 강의 ID
     */
    @Transactional
    public void increaseEnrollmentCount(Long courseId) {
        Course course = findCourseById(courseId);
        course.increaseEnrollmentCount(); // 수강생 수 1 증가 (도메인 메서드)
    }

    /**
     * 추천 서비스(Recommend Service)용 미수강 강의 목록 조회
     * - 특정 카테고리 내에서 이미 수강한 강의(excludeCourseIds)를 제외하고 수강생 수 내림차순 정렬하여 반환
     *
     * @param category         대상 카테고리
     * @param excludeCourseIds 수강생이 이미 수강 중인 강의 ID 리스트
     * @return 추천 대상 강의 DTO 리스트 (수강생 수 인기순 정렬)
     */
    public List<CourseDto.CourseResponse> getRecommendCourses(
            Course.Category category, List<Long> excludeCourseIds) {

        // 1. 이미 수강한 강의 제외 조건 적용하여 DB 조회
        List<Course> courses = excludeCourseIds.isEmpty()
                ? courseRepository.findByCategoryAndStatus(category, Course.Status.ACTIVE)
                : courseRepository.findByCategoryAndStatusAndIdNotIn(
                        category, Course.Status.ACTIVE, excludeCourseIds);

        // 2. 수강생 수(enrollmentCount) 기준 내림차순 인기순 정렬
        return courses.stream()
                .sorted((a, b) -> b.getEnrollmentCount() - a.getEnrollmentCount())
                .map(CourseDto.CourseResponse::from)
                .collect(Collectors.toList());
    }

    /**
     * 내부 헬퍼 메서드: ID 기반 강의 조회 (미존재 시 Exception 발생)
     */
    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("강의를 찾을 수 없습니다: " + id));
    }
}

