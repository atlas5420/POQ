package com.poq.domain.entry.dto;

import com.poq.domain.entry.entity.Entry;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class EntryResponse {

    private UUID entryId;
    private UUID petId;
    private UUID questionId;
    private String questionCategory;
    private String questionText;
    private String answerText;
    private String photoUrl;
    private String tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate entryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public EntryResponse(Entry entry) {
        this.entryId = entry.getId();
        this.petId = entry.getPet().getId();
        this.questionId = entry.getQuestion().getId();
        this.questionCategory = entry.getQuestion().getCategory();
        this.questionText = entry.getQuestion().getText();
        this.answerText = entry.getAnswerText();
        this.photoUrl = entry.getPhotoUrl();
        this.tags = entry.getTags();
        this.entryDate = entry.getEntryDate();
        this.createdAt = entry.getCreatedAt();
    }
}
