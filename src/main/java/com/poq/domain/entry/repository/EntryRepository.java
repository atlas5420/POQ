package com.poq.domain.entry.repository;

import com.poq.domain.entry.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {

    List<Entry> findByPetIdOrderByEntryDateDesc(UUID petId);

    List<Entry> findByPetIdAndEntryDateBetween(UUID petId, LocalDate startDate, LocalDate endDate);
}
