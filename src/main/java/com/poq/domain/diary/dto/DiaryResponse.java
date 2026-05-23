package com.poq.domain.diary.dto;

import com.poq.domain.diary.entity.Diary;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class DiaryResponse {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate diaryDate;

    public DiaryResponse(Diary diary) {
        this.id = diary.getId();
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.imageUrl = diary.getImageUrl();
        this.diaryDate = diary.getDiaryDate();
    }
}