package com.poq.domain.pet.controller;

import com.poq.domain.pet.dto.PetRegisterRequest;
import com.poq.domain.pet.dto.PetResponse;
import com.poq.domain.pet.service.PetService;
import com.poq.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerPet(@Valid @RequestBody PetRegisterRequest request) {
        Long petId = petService.registerPet(request);
        return ResponseEntity.ok(ApiResponse.success("반려동물 등록 성공! 펫 ID: " + petId));
    }

    @GetMapping("/owner/{userId}")
    public ResponseEntity<ApiResponse<List<PetResponse>>> getMyPets(@PathVariable("userId") Long userId) {
        List<com.poq.domain.pet.entity.Pet> pets = petService.getPetsByUserId(userId);
        
        List<PetResponse> response = pets.stream()
                .map(PetResponse::new)
                .toList();
                
        return ResponseEntity.ok(ApiResponse.success(response));
    }

}