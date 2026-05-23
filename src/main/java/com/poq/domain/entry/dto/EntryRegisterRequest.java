package com.poq.domain.entry.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class EntryRegisterRequest {

    @NotNull(message = "반려동물 ID는 필수입니다.")
    private UUID petId;

    @NotBlank(message = "답변 내용은 공백일 수 없습니다.")
    private String answerText;

    private String photoUrl;

    private String tags; // 예: "산책,행복"

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate entryDate; // null 이면 오늘 날짜로 처리
}
