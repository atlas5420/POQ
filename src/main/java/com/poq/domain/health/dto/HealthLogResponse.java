package com.poq.domain.health.dto;

import com.poq.domain.health.entity.HealthLog;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class HealthLogResponse {
    
    private Long id;
    private Double weight;
    private String memo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate logDate;

    public HealthLogResponse(HealthLog healthLog) {
        this.id = healthLog.getId();
        this.weight = healthLog.getWeight();
        this.memo = healthLog.getMemo();
        this.logDate = healthLog.getLogDate();
    }
}