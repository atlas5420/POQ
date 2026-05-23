package com.poq.domain.diary.service;

import com.poq.domain.diary.dto.DiaryRegisterRequest;
import com.poq.domain.diary.entity.Diary;
import com.poq.domain.diary.repository.DiaryRepository;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final PetRepository petRepository;

    @Transactional
    public Long registerDiary(DiaryRegisterRequest request) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물입니다."));

        Diary diary = new Diary(
                pet,
                request.getTitle(),
                request.getContent(),
                request.getImageUrl(),
                request.getDiaryDate()
        );

        return diaryRepository.save(diary).getId();
    }

    public List<Diary> getDiariesByPetId(Long petId) {
        return diaryRepository.findByPetIdOrderByDiaryDateDesc(petId);
    }
    
    public List<Diary> searchPetDiaries(Long petId, String keyword) {
        return diaryRepository.searchDiaries(petId, keyword);
    }
}