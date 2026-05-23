package com.poq.domain.walk.dto;

import com.poq.domain.walk.entity.WalkLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WalkLogResponse {
    
    private Long id;
    private Integer durationMinutes;
    private String memo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate walkDate;

    public WalkLogResponse(WalkLog walkLog) {
        this.id = walkLog.getId();
        this.durationMinutes = walkLog.getDurationMinutes();
        this.memo = walkLog.getMemo();
        this.walkDate = walkLog.getWalkDate();
    }
}