package com.poq.domain.user.controller;

import com.poq.domain.user.dto.UserSignUpRequest;
import com.poq.domain.user.service.UserService;
import com.poq.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> signUp(@RequestBody UserSignUpRequest request) {
        Long userId = userService.signUp(request);
        return ResponseEntity.ok(ApiResponse.success("회원가입 성공! 생성된 유저 ID: " + userId));
    }
}