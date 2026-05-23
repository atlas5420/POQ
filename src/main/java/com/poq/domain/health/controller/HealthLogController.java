package com.poq.domain.health.controller;

import com.poq.domain.health.dto.HealthLogRegisterRequest;
import com.poq.domain.health.dto.HealthLogResponse;
import com.poq.domain.health.dto.HealthLogUpdateRequest;
import com.poq.domain.health.service.HealthLogService;
import com.poq.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/health")
@RequiredArgsConstructor
public class HealthLogController {

    private final HealthLogService healthLogService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerHealthLog(@Valid @RequestBody HealthLogRegisterRequest request) {
        Long logId = healthLogService.registerHealthLog(request);
        return ResponseEntity.ok(ApiResponse.success("건강 기록 등록 성공! 기록 ID: " + logId));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<HealthLogResponse>>> getPetHealthLogs(@PathVariable("petId") Long petId) {
        List<com.poq.domain.health.entity.HealthLog> logs = healthLogService.getHealthLogsByPetId(petId);
        
        List<HealthLogResponse> response = logs.stream()
                .map(HealthLogResponse::new)
                .toList();
                
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{logId}")
    public ResponseEntity<ApiResponse<String>> updateHealthLog(
            @PathVariable("logId") Long logId, 
            @RequestBody HealthLogUpdateRequest request) {
        healthLogService.updateLog(logId, request);
        return ResponseEntity.ok(ApiResponse.success("건강 기록 수정 성공!"));
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<ApiResponse<String>> deleteHealthLog(@PathVariable("logId") Long logId) {
        healthLogService.deleteLog(logId);
        return ResponseEntity.ok(ApiResponse.success("건강 기록 삭제 성공!"));
    }
}