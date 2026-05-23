package com.poq.domain.user.repository;

import com.poq.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 로그인 처리나 중복 가입 체크 시 ID로 유저를 찾기 위한 쿼리 메소드
    Optional<User> findByUsername(String username);
}