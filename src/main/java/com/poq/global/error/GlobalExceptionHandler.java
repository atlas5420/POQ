package com.poq.global.error;

import com.poq.global.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException; // 💡 정확한 패키지 임포트
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. DTO 유효성 검증(@Valid) 실패 예외를 가로채서 핵심만 필터링
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                               .getFieldError()
                               .getDefaultMessage();
        
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.fail(errorMessage));
    }

    // 2. 비즈니스 로직 예외 (IllegalArgumentException, IllegalStateException)
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body(ApiResponse.fail(e.getMessage()));
    }

    // 3. 그 외 시스템 전역 예기치 못한 에러 관제
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleServerException(Exception e) {
        return ResponseEntity
                .internalServerError()
                .body(ApiResponse.fail("서버 내부 오류가 발생했습니다: " + e.getMessage()));
    }
}