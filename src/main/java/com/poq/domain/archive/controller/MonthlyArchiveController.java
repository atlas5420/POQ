package com.poq.domain.archive.controller;

import com.poq.domain.archive.dto.MonthlyArchiveResponse;
import com.poq.domain.archive.entity.MonthlyArchive;
import com.poq.domain.archive.service.MonthlyArchiveService;
import com.poq.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/monthly-archives")
@RequiredArgsConstructor
public class MonthlyArchiveController {

    private final MonthlyArchiveService archiveService;

    /**
     * 특정 반려동물의 전체 월간 아카이브 조회
     */
    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<MonthlyArchiveResponse>>> getArchives(@PathVariable("petId") UUID petId) {
        List<MonthlyArchive> archives = archiveService.getArchivesByPetId(petId);
        List<MonthlyArchiveResponse> response = archives.stream()
                .map(MonthlyArchiveResponse::new)
                .toList();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 수동 월간 아카이브 생성/집계 트리거 (테스트용)
     * 예: POST /api/monthly-archives/pet/{petId}/compile?month=2026-05
     */
    @PostMapping("/pet/{petId}/compile")
    public ResponseEntity<ApiResponse<MonthlyArchiveResponse>> compileArchive(
            @PathVariable("petId") UUID petId,
            @RequestParam("month") String month) {
        MonthlyArchive archive = archiveService.compileMonthlyArchive(petId, month);
        return ResponseEntity.ok(ApiResponse.success(new MonthlyArchiveResponse(archive)));
    }
}
