package com.poq.domain.entry.service;

import com.poq.domain.assignment.entity.DailyAssignment;
import com.poq.domain.assignment.service.DailyAssignmentService;
import com.poq.domain.entry.dto.EntryRegisterRequest;
import com.poq.domain.entry.entity.Entry;
import com.poq.domain.entry.repository.EntryRepository;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EntryService {

    private final EntryRepository entryRepository;
    private final PetService petService;
    private final DailyAssignmentService assignmentService;

    /**
     * 답변 기록 작성 및 배정 상태 COMPLETED 처리
     */
    @Transactional
    public UUID createEntry(EntryRegisterRequest request) {
        Pet pet = petService.getPetById(request.getPetId());
        LocalDate date = request.getEntryDate() != null ? request.getEntryDate() : LocalDate.now();

        // 1. 해당 날짜의 일일 배정 조회 (없으면 즉석 배정 생성)
        DailyAssignment assignment = assignmentService.getTodayAssignmentForPet(pet.getId());

        // 2. 답변 작성
        Entry entry = Entry.builder()
                .user(pet.getUser())
                .pet(pet)
                .question(assignment.getQuestion())
                .answerText(request.getAnswerText())
                .photoUrl(request.getPhotoUrl())
                .tags(request.getTags())
                .entryDate(date)
                .build();

        Entry savedEntry = entryRepository.save(entry);

        // 3. 일일 배정 상태 COMPLETED 로 업데이트
        assignment.complete();

        return savedEntry.getId();
    }

    /**
     * 반려동물의 전체 기록 조회
     */
    public List<Entry> getEntriesByPetId(UUID petId) {
        return entryRepository.findByPetIdOrderByEntryDateDesc(petId);
    }

    /**
     * 특정 기간 내 반려동물의 기록 조회 (아카이브용)
     */
    public List<Entry> getEntriesByPetIdAndDateRange(UUID petId, LocalDate start, LocalDate end) {
        return entryRepository.findByPetIdAndEntryDateBetween(petId, start, end);
    }
}
