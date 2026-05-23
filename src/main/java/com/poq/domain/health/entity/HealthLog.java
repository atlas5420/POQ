package com.poq.domain.health.entity;

import com.poq.domain.pet.entity.Pet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_logs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet; // 이 기록이 속한 반려동물 (외래키)

    @Column(nullable = false)
    private Double weight; // 반려동물 몸무게 (예: 5.4)

    @Column(name = "log_date", nullable = false)
    private LocalDate logDate; // 기록 기준일 (과거 날짜 기록 가능하도록 LocalDate 사용)

    @Column(columnDefinition = "TEXT")
    private String memo; // 건강 특이사항 및 메모

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // 엔티티 생성을 위한 생성자
    public HealthLog(Pet pet, Double weight, LocalDate logDate, String memo) {
        this.pet = pet;
        this.weight = weight;
        this.logDate = logDate;
        this.memo = memo;
        this.createdAt = LocalDateTime.now();
    }
    
    // HealthLog.java 클래스 내부 맨 밑에 아래 메서드를 추가해 주세요.
    public void update(Double weight, LocalDate logDate, String memo) {
        this.weight = weight;
        this.logDate = logDate;
        this.memo = memo;
    }
    
}