package com.poq.domain.entry.entity;

import com.poq.domain.user.entity.User;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.question.entity.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "entries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Entry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(length = 255)
    private String tags; // 쉼표로 구분된 태그 목록 (예: "산책,신남")

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Entry(User user, Pet pet, Question question, String answerText, String photoUrl, String tags, LocalDate entryDate) {
        this.user = user;
        this.pet = pet;
        this.question = question;
        this.answerText = answerText;
        this.photoUrl = photoUrl;
        this.tags = tags;
        this.entryDate = entryDate;
        this.createdAt = LocalDateTime.now();
    }
}
