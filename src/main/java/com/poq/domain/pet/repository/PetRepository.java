package com.poq.domain.pet.repository;

import com.poq.domain.pet.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, UUID> {
    List<Pet> findByUserId(UUID userId);
}