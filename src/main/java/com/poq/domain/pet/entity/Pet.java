package com.poq.domain.pet.entity;

import com.poq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 💡 오타 수정 완료
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String type;

    @Column(length = 50)
    private String breed;

    @Column(name = "birth_date")
    private LocalDate birthDate; // 표준 LocalDate 사용

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 💡 잘려있던 생성자 매개변수와 내부 대입문 완벽 복구
    public Pet(User user, String name, String type, String breed, LocalDate birthDate) {
        this.user = user;
        this.name = name;
        this.type = type;
        this.breed = breed;
        this.birthDate = birthDate;
        this.createdAt = LocalDateTime.now();
    }
}