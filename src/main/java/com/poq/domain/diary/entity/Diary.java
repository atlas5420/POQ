package com.poq.domain.diary.entity;

import com.poq.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "diaries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet; // 일기의 주인공 펫 (외래키)

    @Column(nullable = false, length = 100)
    private String title; // 일기 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 일기 내용

    @Column(name = "image_url")
    private String imageUrl; // 첨부 이미지 경로 (우선은 문자열로 처리)

    @Column(name = "diary_date", nullable = false)
    private LocalDate diaryDate; // 일기 작성 기준일

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Diary(Pet pet, String title, String content, String imageUrl, LocalDate diaryDate) {
        this.pet = pet;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.diaryDate = diaryDate;
        this.createdAt = LocalDateTime.now();
    }
}