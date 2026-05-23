package com.poq.domain.archive.dto;

import com.poq.domain.archive.entity.MonthlyArchive;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class MonthlyArchiveResponse {

    private UUID archiveId;
    private UUID petId;
    private String month;
    private String summaryText;
    private String coverPhotoUrl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime generatedAt;

    public MonthlyArchiveResponse(MonthlyArchive archive) {
        this.archiveId = archive.getId();
        this.petId = archive.getPet().getId();
        this.month = archive.getMonth();
        this.summaryText = archive.getSummaryText();
        this.coverPhotoUrl = archive.getCoverPhotoUrl();
        this.generatedAt = archive.getGeneratedAt();
    }
}
