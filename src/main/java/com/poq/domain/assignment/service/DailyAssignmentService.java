package com.poq.domain.assignment.service;

import com.poq.domain.assignment.entity.AssignmentStatus;
import com.poq.domain.assignment.entity.DailyAssignment;
import com.poq.domain.assignment.repository.DailyAssignmentRepository;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.service.PetService;
import com.poq.domain.question.entity.Question;
import com.poq.domain.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyAssignmentService {

    private final DailyAssignmentRepository assignmentRepository;
    private final QuestionRepository questionRepository;
    private final PetService petService;

    /**
     * 특정 펫의 오늘의 질문 조회 (없으면 즉석에서 랜덤 배정 생성)
     */
    @Transactional
    public DailyAssignment getTodayAssignmentForPet(UUID petId) {
        LocalDate today = LocalDate.now();
        Optional<DailyAssignment> assignmentOpt = assignmentRepository.findByPetIdAndAssignedDate(petId, today);
        if (assignmentOpt.isPresent()) {
            return assignmentOpt.get();
        }

        // 오늘의 배정이 없는 경우 실시간 자동 생성 처리
        Pet pet = petService.getPetById(petId);
        return assignTodayQuestionForPet(pet, today);
    }

    /**
     * 특정 펫에게 지정된 날짜의 질문을 배정합니다.
     */
    @Transactional
    public DailyAssignment assignTodayQuestionForPet(Pet pet, LocalDate date) {
        // 이미 해당 날짜에 배정된 질문이 있다면 리턴
        Optional<DailyAssignment> existing = assignmentRepository.findByPetIdAndAssignedDate(pet.getId(), date);
        if (existing.isPresent()) {
            return existing.get();
        }

        // 해당 펫의 종에 맞는 활성 질문 목록 조회
        List<Question> questions = questionRepository.findActiveQuestionsBySpecies(pet.getSpecies());
        if (questions.isEmpty()) {
            // 만약 전용/공통 질문이 아예 없으면 전체 활성 질문 조회
            questions = questionRepository.findByActiveTrue();
        }

        if (questions.isEmpty()) {
            throw new IllegalStateException("배정할 수 있는 활성화된 질문이 존재하지 않습니다.");
        }

        // 무작위 셔플 후 첫 번째 질문 선택
        Collections.shuffle(questions);
        Question selectedQuestion = questions.get(0);

        DailyAssignment assignment = DailyAssignment.builder()
                .user(pet.getUser())
                .pet(pet)
                .question(selectedQuestion)
                .assignedDate(date)
                .status(AssignmentStatus.PENDING)
                .build();

        return assignmentRepository.save(assignment);
    }
}
