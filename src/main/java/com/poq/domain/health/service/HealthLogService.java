package com.poq.domain.health.service;

import com.poq.domain.health.dto.HealthLogRegisterRequest;
import com.poq.domain.health.dto.HealthLogUpdateRequest;
import com.poq.domain.health.entity.HealthLog;
import com.poq.domain.health.repository.HealthLogRepository;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HealthLogService {

    private final HealthLogRepository healthLogRepository;
    private final PetRepository petRepository;

    // 1. 건강 기록 등록
    @Transactional
    public Long registerHealthLog(HealthLogRegisterRequest request) {
        Pet pet = petRepository.findById(request.getPetId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물입니다."));

        HealthLog healthLog = new HealthLog(
                pet, 
                request.getWeight(), 
                request.getLogDate(), 
                request.getMemo()
        );

        HealthLog savedLog = healthLogRepository.save(healthLog);
        return savedLog.getId();
    }

    // 2. 특정 반려동물의 건강 기록 최신순 조회
    public List<HealthLog> getHealthLogsByPetId(Long petId) {
        return healthLogRepository.findByPetIdOrderByLogDateDesc(petId);
    }
    
    // 3. 건강 기록 수정 (변경 감지 활용)
    @Transactional
    public void updateLog(Long logId, HealthLogUpdateRequest request) {
        HealthLog healthLog = healthLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 건강 기록입니다."));
                
        // 엔티티의 값만 바꾸면 트랜잭션이 끝날 때 자동으로 update 쿼리가 나갑니다.
        healthLog.update(request.getWeight(), request.getLogDate(), request.getMemo());
    }

    // 4. 건강 기록 삭제
    @Transactional
    public void deleteLog(Long logId) {
        HealthLog healthLog = healthLogRepository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 건강 기록입니다."));
                
        healthLogRepository.delete(healthLog);
    }
}