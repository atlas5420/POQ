package com.poq.domain.walk.entity;

import com.poq.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "walk_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet; // 산책을 다녀온 반려동물 외래키

    @Column(name = "walk_date", nullable = false)
    private LocalDate walkDate; // 산책한 날짜

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes; // 산책 소요 시간 (분 단위)

    @Column(columnDefinition = "TEXT")
    private String memo; // 산책 일지 및 메모

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public WalkLog(Pet pet, LocalDate walkDate, Integer durationMinutes, String memo) {
        this.pet = pet;
        this.walkDate = walkDate;
        this.durationMinutes = durationMinutes;
        this.memo = memo;
        this.createdAt = LocalDateTime.now();
    }
}