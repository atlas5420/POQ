package com.poq.domain.archive.repository;

import com.poq.domain.archive.entity.MonthlyArchive;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MonthlyArchiveRepository extends JpaRepository<MonthlyArchive, UUID> {

    List<MonthlyArchive> findByPetIdOrderByMonthDesc(UUID petId);

    Optional<MonthlyArchive> findByPetIdAndMonth(UUID petId, String month);
}
