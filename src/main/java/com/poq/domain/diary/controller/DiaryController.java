package com.poq.domain.diary.controller;

import com.poq.domain.diary.dto.DiaryRegisterRequest;
import com.poq.domain.diary.dto.DiaryResponse;
import com.poq.domain.diary.entity.Diary;
import com.poq.domain.diary.service.DiaryService;
import com.poq.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/diaries")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    // 1. 일기 등록 API
    // POST http://localhost:8080/api/diaries/register
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerDiary(@RequestBody DiaryRegisterRequest request) {
        Long diaryId = diaryService.registerDiary(request);
        return ResponseEntity.ok(ApiResponse.success("일상 다이어리 등록 성공! 일기 ID: " + diaryId));
    }

    // 2. 특정 반려동물의 일기 목록 조회 API
    // GET http://localhost:8080/api/diaries/pet/1
    @GetMapping("/pet/{petId}")
    public ResponseEntity<ApiResponse<List<DiaryResponse>>> getPetDiaries(@PathVariable("petId") Long petId) {
        List<com.poq.domain.diary.entity.Diary> diaries = diaryService.getDiariesByPetId(petId);
        
        List<DiaryResponse> response = diaries.stream()
                .map(DiaryResponse::new)
                .toList();
                
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/pet/{petId}/search")
    public ResponseEntity<ApiResponse<List<DiaryResponse>>> searchDiaries(
            @PathVariable("petId") Long petId,
            @RequestParam(value = "keyword", required = false) String keyword) {
            
        List<Diary> diaries = diaryService.searchPetDiaries(petId, keyword);
        
        List<DiaryResponse> response = diaries.stream()
                .map(DiaryResponse::new)
                .toList();
                
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}