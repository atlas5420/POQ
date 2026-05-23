package com.poq.global.common;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private boolean success; // 성공 여부 (true/false)
    private T data;          // 실제 응답 데이터 (성공 시)
    private String message;   // 에러 메시지 (실패 시)

    // 성공 응답 생성자
    private ApiResponse(boolean success, T data, String message) {
        this.success = success;
        this.data = data;
        this.message = message;
    }

    // 성공했을 때 호출할 스태틱 메서드 (데이터가 있는 경우)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data, null);
    }

    // 성공했을 때 호출할 스태틱 메서드 (단순 알림 메시지만 있는 경우)
    public static ApiResponse<String> success(String message) {
        return new ApiResponse<>(true, message, null);
    }

    // 실패했을 때 호출할 스태틱 메서드
    public static ApiResponse<Void> fail(String message) {
        return new ApiResponse<>(false, null, message);
    }
}