package com.poq.domain.health.repository;

import com.poq.domain.health.entity.HealthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HealthLogRepository extends JpaRepository<HealthLog, Long> {
    // 특정 반려동물의 건강 기록을 최신 날짜 순으로 조회
    List<HealthLog> findByPetIdOrderByLogDateDesc(Long petId);
}