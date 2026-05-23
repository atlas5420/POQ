package com.poq.domain.assignment.controller;

import com.poq.domain.assignment.dto.DailyAssignmentResponse;
import com.poq.domain.assignment.entity.DailyAssignment;
import com.poq.domain.assignment.service.DailyAssignmentService;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.service.PetService;
import com.poq.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/daily-assignments")
@RequiredArgsConstructor
public class DailyAssignmentController {

    private final DailyAssignmentService assignmentService;
    private final PetService petService;

    /**
     * 특정 반려동물의 오늘의 질문 배정 조회 (없으면 즉석 배정 생성)
     */
    @GetMapping("/pet/{petId}/today")
    public ResponseEntity<ApiResponse<DailyAssignmentResponse>> getTodayAssignment(@PathVariable("petId") UUID petId) {
        DailyAssignment assignment = assignmentService.getTodayAssignmentForPet(petId);
        return ResponseEntity.ok(ApiResponse.success(new DailyAssignmentResponse(assignment)));
    }

    /**
     * 수동으로 특정 반려동물에게 오늘의 질문 배정을 트리거/수행 (테스트용)
     */
    @PostMapping("/pet/{petId}/assign")
    public ResponseEntity<ApiResponse<DailyAssignmentResponse>> assignTodayAssignment(@PathVariable("petId") UUID petId) {
        Pet pet = petService.getPetById(petId);
        DailyAssignment assignment = assignmentService.assignTodayQuestionForPet(pet, LocalDate.now());
        return ResponseEntity.ok(ApiResponse.success(new DailyAssignmentResponse(assignment)));
    }
}
