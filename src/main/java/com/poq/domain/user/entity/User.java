package com.poq.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // PostgreSQL에서 user는 예약어일 수 있으므로 테이블명은 안전하게 users로 지정합니다.
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username; // 로그인 ID

    @Column(nullable = false, length = 100)
    private String password; // 암호화된 비밀번호

    @Column(nullable = false, length = 50)
    private String name; // 사용자 본명

    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 엔티티 생성을 위한 빌더나 생성자 메소드
    public User(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
}