package com.poq.domain.pet.service;

import com.poq.domain.pet.dto.PetRegisterRequest;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.repository.PetRepository;
import com.poq.domain.user.entity.User;
import com.poq.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {

    private final PetRepository petRepository;
    private final UserService userService;

    /**
     * 반려동물 등록 로직
     */
    @Transactional
    public UUID registerPet(PetRegisterRequest request) {
        // 1. 유저 존재 여부 검증
        User user = userService.getUserById(request.getUserId());

        // 2. DTO -> Entity 변환 후 저장
        Pet pet = request.toEntity(user);
        Pet savedPet = petRepository.save(pet);

        return savedPet.getId();
    }

    /**
     * 특정 유저의 반려동물 목록 조회 로직
     */
    public List<Pet> getPetsByUserId(UUID userId) {
        return petRepository.findByUserId(userId);
    }

    public Pet getPetById(UUID id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 반려동물입니다."));
    }

    /**
     * 전체 반려동물 목록 조회 (스케줄러용)
     */
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}