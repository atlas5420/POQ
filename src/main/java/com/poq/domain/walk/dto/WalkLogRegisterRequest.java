package com.poq.domain.walk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class WalkLogRegisterRequest {
    
    private Long petId;              // 어떤 펫이 산책했는지
    private Integer durationMinutes; // 산책 시간 (분)
    private String memo;             // 산책 일지 내용

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate walkDate;      // 산책 날짜
}