package com.poq.domain.pet.dto;

import com.poq.domain.pet.entity.Pet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PetResponse {
    
    private Long id;
    private String name;
    private String type;
    private String breed;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.type = pet.getType();
        this.breed = pet.getBreed();
        this.birthDate = pet.getBirthDate();
    }
}