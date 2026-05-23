package com.poq.domain.assignment.repository;

import com.poq.domain.assignment.entity.DailyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DailyAssignmentRepository extends JpaRepository<DailyAssignment, UUID> {

    Optional<DailyAssignment> findByPetIdAndAssignedDate(UUID petId, LocalDate assignedDate);

    List<DailyAssignment> findByPetId(UUID petId);
}
