package com.poq.domain.pet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class PetRegisterRequest {

    @NotNull(message = "유저 ID는 필수입니다.")
    private UUID userId;

    @NotBlank(message = "반려동물 이름은 필수입니다.")
    private String name;

    @NotBlank(message = "반려동물 종류(species)는 필수입니다. (예: 강아지, 고양이)")
    private String species;

    private String breed;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthday;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate adoptionDay;

    private String gender;

    private String photoUrl;

    private String notes;

    public Pet toEntity(User user) {
        return Pet.builder()
                .user(user)
                .name(this.name)
                .species(this.species)
                .breed(this.breed)
                .birthday(this.birthday)
                .adoptionDay(this.adoptionDay)
                .gender(this.gender)
                .photoUrl(this.photoUrl)
                .notes(this.notes)
                .build();
    }
}