package com.poq.domain.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(length = 100)
    private String nickname;

    @Column(nullable = false, length = 50)
    private String provider; // email, google, apple 등

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public User(String email, String nickname, String provider) {
        this.email = email;
        this.nickname = nickname;
        this.provider = provider != null ? provider : "email";
        this.createdAt = LocalDateTime.now();
    }
}