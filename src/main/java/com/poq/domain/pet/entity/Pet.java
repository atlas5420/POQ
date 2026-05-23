package com.poq.domain.pet.entity;

import com.poq.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pets")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 50)
    private String species; // 강아지, 고양이 등

    @Column(length = 100)
    private String breed;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "adoption_day")
    private LocalDate adoptionDay;

    @Column(length = 10)
    private String gender;

    @Column(name = "photo_url", length = 500)
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public Pet(User user, String name, String species, String breed, LocalDate birthday, LocalDate adoptionDay, String gender, String photoUrl, String notes) {
        this.user = user;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.birthday = birthday;
        this.adoptionDay = adoptionDay;
        this.gender = gender;
        this.photoUrl = photoUrl;
        this.notes = notes;
        this.createdAt = LocalDateTime.now();
    }
}