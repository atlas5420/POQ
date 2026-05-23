package com.poq.domain.question.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String category; // 습관, 산책, 소리 등

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text; // 질문 내용

    @Column(name = "species_type", length = 50)
    private String speciesType; // DOG, CAT 또는 null(공통)

    @Column(nullable = false)
    private boolean active;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Question(String category, String text, String speciesType, boolean active) {
        this.category = category;
        this.text = text;
        this.speciesType = speciesType;
        this.active = active;
        this.createdAt = LocalDateTime.now();
    }
}
