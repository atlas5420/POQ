package com.poq.domain.assignment.entity;

import com.poq.domain.user.entity.User;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.question.entity.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Builder;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "daily_assignments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyAssignment {

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

    @Column(name = "assigned_date", nullable = false)
    private LocalDate assignedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AssignmentStatus status;

    @Builder
    public DailyAssignment(User user, Pet pet, Question question, LocalDate assignedDate, AssignmentStatus status) {
        this.user = user;
        this.pet = pet;
        this.question = question;
        this.assignedDate = assignedDate;
        this.status = status != null ? status : AssignmentStatus.PENDING;
    }

    public void complete() {
        this.status = AssignmentStatus.COMPLETED;
    }
}
