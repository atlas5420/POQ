package com.poq.domain.pet.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poq.domain.pet.entity.Pet;
import com.poq.domain.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PetRegisterRequest {
    
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId; 

    @NotBlank(message = "반려동물 이름은 공백일 수 없습니다.")
    private String name;

    @NotBlank(message = "반려동물 종류(DOG, CAT 등)는 필수입니다.")
    private String type;

    private String breed; 

    @NotNull(message = "생년월일은 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthDate; 

    // 💡 누락되었을 수 있는 엔티티 변환 메서드를 명확히 추가합니다.
    public Pet toEntity(User user) {
        return new Pet(user, this.name, this.type, this.breed, this.birthDate);
    }
}