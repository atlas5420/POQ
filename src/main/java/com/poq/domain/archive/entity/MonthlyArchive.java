package com.poq.domain.archive.entity;

import com.poq.domain.user.entity.User;
import com.poq.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "monthly_archives")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthlyArchive {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @Column(nullable = false, length = 10)
    private String month; // YYYY-MM 형식

    @Column(name = "summary_text", columnDefinition = "TEXT")
    private String summaryText; // 대표 문장 및 분석 요약

    @Column(name = "cover_photo_url", length = 500)
    private String coverPhotoUrl; // 월간 대표 이미지 URL

    @Column(name = "generated_at", nullable = false)
    private LocalDateTime generatedAt;

    @Builder
    public MonthlyArchive(User user, Pet pet, String month, String summaryText, String coverPhotoUrl) {
        this.user = user;
        this.pet = pet;
        this.month = month;
        this.summaryText = summaryText;
        this.coverPhotoUrl = coverPhotoUrl;
        this.generatedAt = LocalDateTime.now();
    }

    /**
     * 아카이브 내용 업데이트 (재집계 시 사용)
     */
    public void update(String summaryText, String coverPhotoUrl) {
        this.summaryText = summaryText;
        this.coverPhotoUrl = coverPhotoUrl;
        this.generatedAt = LocalDateTime.now();
    }
}
