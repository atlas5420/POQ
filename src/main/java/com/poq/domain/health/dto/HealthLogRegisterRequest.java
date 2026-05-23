package com.poq.domain.health.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive; // 어노테이션 추가
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class HealthLogRegisterRequest {
    
    @NotNull(message = "펫 ID는 필수입니다.")
    private Long petId;       

    @NotNull(message = "몸무게는 필수입니다.")
    @Positive(message = "몸무게는 0보다 커야 합니다.") // 💡 음수나 0 차단
    private Double weight;     

    private String memo;       

    @NotNull(message = "기록 날짜는 필수입니다.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate logDate; 
}