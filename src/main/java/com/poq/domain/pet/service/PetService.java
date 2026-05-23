package com.poq.domain.pet.service;

import com.poq.domain.pet.dto.PetRegisterRequest;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.pet.repository.PetRepository;
import com.poq.domain.user.entity.User;
import com.poq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {

    private final PetRepository petRepository;
    private final UserRepository userRepository;

    /**
     * 반려동물 등록 로직
     */
    @Transactional
    public Long registerPet(PetRegisterRequest request) {
        // 1. 유저(주인) 존재 여부 검증
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 2. DTO -> Entity 변환 후 저장
        Pet pet = request.toEntity(user);
        Pet savedPet = petRepository.save(pet);

        return savedPet.getId();
    }

    /**
     * 특정 유저의 반려동물 목록 조회 로직
     */
    public List<Pet> getPetsByUserId(Long userId) {
        return petRepository.findByUserId(userId);
    }
}