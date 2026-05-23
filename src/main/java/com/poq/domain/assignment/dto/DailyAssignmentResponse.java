package com.poq.domain.assignment.dto;

import com.poq.domain.assignment.entity.AssignmentStatus;
import com.poq.domain.assignment.entity.DailyAssignment;
import lombok.Getter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class DailyAssignmentResponse {

    private UUID assignmentId;
    private UUID petId;
    private UUID questionId;
    private String category;
    private String questionText;
    private LocalDate assignedDate;
    private AssignmentStatus status;

    public DailyAssignmentResponse(DailyAssignment assignment) {
        this.assignmentId = assignment.getId();
        this.petId = assignment.getPet().getId();
        this.questionId = assignment.getQuestion().getId();
        this.category = assignment.getQuestion().getCategory();
        this.questionText = assignment.getQuestion().getText();
        this.assignedDate = assignment.getAssignedDate();
        this.status = assignment.getStatus();
    }
}
