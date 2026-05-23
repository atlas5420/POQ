package com.poq.domain.user.service;

import com.poq.domain.user.dto.UserSignUpRequest;
import com.poq.domain.user.entity.User;
import com.poq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 가입 비즈니스 로직
     */
    @Transactional
    public Long signUp(UserSignUpRequest request) {
        // 1. 중복 아이디 검증
        userRepository.findByUsername(request.getUsername())
                .ifPresent(user -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });

        // 2. DTO -> Entity 변환 후 DB 저장 (실무에서는 여기서 password 암호화가 들어갑니다)
        User user = request.toEntity();
        User savedUser = userRepository.save(user);

        // 3. 생성된 고유 ID 반환
        return savedUser.getId();
    }
}