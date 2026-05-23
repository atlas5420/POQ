package com.poq.domain.walk.service;

import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.repository.PetRepository;
import com.poq.domain.walk.dto.WalkLogRegisterRequest;
import com.poq.domain.walk.entity.WalkLog;
import com.poq.domain.walk.repository.WalkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WalkLogService {

    private final WalkLogRepository walkLogRepository;
    private final PetRepository petRepository;

    // 1. 산책 기록 등록
    @Transactional
    public Long registerWalkLog(WalkLogRegisterRequest request) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물입니다."));

        WalkLog walkLog = new WalkLog(
                pet,
                request.getWalkDate(),
                request.getDurationMinutes(),
                request.getMemo()
        );

        WalkLog savedLog = walkLogRepository.save(walkLog);
        return savedLog.getId();
    }

    // 2. 특정 반려동물의 산책 기록 최신순 조회
    public List<WalkLog> getWalkLogsByPetId(Long petId) {
    	return walkLogRepository.findByPetIdWithPet(petId);
    }
}