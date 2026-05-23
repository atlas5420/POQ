package com.poq.domain.walk.repository;

import com.poq.domain.walk.entity.WalkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface WalkLogRepository extends JpaRepository<WalkLog, Long> {

    // 💡 join fetch p.pet을 통해 WalkLog를 가져올 때 연관된 Pet까지 쿼리 한 방(INNER JOIN)으로 긁어옵니다.
    @Query("select w from WalkLog w join fetch w.pet p where p.id = :petId order by w.walkDate desc")
    List<WalkLog> findByPetIdWithPet(@Param("petId") Long petId);
}