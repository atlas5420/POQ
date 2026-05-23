package com.poq.domain.user.service;

import com.poq.domain.user.dto.UserLoginRequest;
import com.poq.domain.user.entity.User;
import com.poq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    /**
     * 소셜 로그인 / 회원가입 시뮬레이션
     * 이미 가입된 이메일인 경우 기존 사용자 ID 반환.
     * 새로운 이메일인 경우 회원가입 후 생성된 사용자 ID 반환.
     */
    @Transactional
    public UUID loginOrSignUp(UserLoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .map(User::getId)
                .orElseGet(() -> {
                    User user = User.builder()
                            .email(request.getEmail())
                            .nickname(request.getNickname())
                            .provider(request.getProvider())
                            .build();
                    return userRepository.save(user).getId();
                });
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}