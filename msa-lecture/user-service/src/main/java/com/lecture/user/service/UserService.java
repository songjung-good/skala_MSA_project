package com.lecture.user.service;

import com.lecture.user.dto.UserDto;
import com.lecture.user.entity.User;
import com.lecture.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 비즈니스 로직 서비스
 * - 회원가입, 사용자 정보 조회 등의 데이터 처리 담당
 * - 기본 읽기 전용 트랜잭션(@Transactional(readOnly = true)) 설정으로 조회 성능 최적화
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 처리
     * 1. 이메일 중복 확인 (중복 시 IllegalArgumentException 발생)
     * 2. 권한(Role) 지정 (미지정 시 기본값 STUDENT)
     * 3. BCrypt를 활용한 비밀번호 해시 암호화 처리 후 DB 저장
     *
     * @param request 회원가입 요청 DTO (이메일, 비밀번호, 이름, 권한)
     * @return 저장된 사용자 응답 DTO
     */
    @Transactional
    public UserDto.UserResponse register(UserDto.RegisterRequest request) {
        // 1. 이메일 중복 검증
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다: " + request.getEmail());
        }

        // 2. 권한 설정 (기본 STUDENT)
        User.Role role = request.getRole() != null ? request.getRole() : User.Role.STUDENT;

        // 3. User 엔티티 생성 및 비밀번호 암호화
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .role(role)
                .build();

        // 4. DB 저장 후 DTO 변환 반환
        User savedUser = userRepository.save(user);
        return UserDto.UserResponse.from(savedUser);
    }

    /**
     * 사용자 PK ID로 사용자 단건 조회
     *
     * @param id 사용자 식별자 (PK)
     * @return 사용자 응답 DTO
     */
    public UserDto.UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
        return UserDto.UserResponse.from(user);
    }

    /**
     * 사용자 이메일로 사용자 조회 (인증 및 내부 서비스 호출용)
     *
     * @param email 사용자 이메일 계정
     * @return 사용자 응답 DTO
     */
    public UserDto.UserResponse getUserByEmail(String email) {
        System.out.println(">>> getUserByEmail email = " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + email));
        return UserDto.UserResponse.from(user);
    }
}

