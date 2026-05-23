package com.poq.domain.pet.dto;

import com.poq.domain.pet.entity.Pet;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class PetResponse {

    private UUID id;
    private String name;
    private String species;
    private String breed;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate adoptionDay;

    private String gender;
    private String photoUrl;
    private String notes;

    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.birthday = pet.getBirthday();
        this.adoptionDay = pet.getAdoptionDay();
        this.gender = pet.getGender();
        this.photoUrl = pet.getPhotoUrl();
        this.notes = pet.getNotes();
    }
}