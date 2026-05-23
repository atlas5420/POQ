package com.poq.domain.walk.controller;

import com.poq.global.common.ApiResponse; // 임포트 추가
import com.poq.domain.walk.dto.WalkLogRegisterRequest;
import com.poq.domain.walk.dto.WalkLogResponse;
import com.poq.domain.walk.service.WalkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/walks")
@RequiredArgsConstructor
public class WalkLogController {

    private final WalkLogService walkLogService;

    // 💡 try-catch가 사라지고 공통 포맷(ApiResponse)으로 응답을 감쌉니다.
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerWalkLog(@RequestBody WalkLogRegisterRequest request) {
        Long walkId = walkLogService.registerWalkLog(request);
        return ResponseEntity.ok(ApiResponse.success("산책 기록 등록 성공! 기록 ID: " + walkId));
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<WalkLogResponse>>> getPetWalkLogs(@PathVariable("petId") Long petId) {
        List<com.poq.domain.walk.entity.WalkLog> walks = walkLogService.getWalkLogsByPetId(petId);
        
        List<WalkLogResponse> response = walks.stream()
                .map(WalkLogResponse::new)
                .toList();
                
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}