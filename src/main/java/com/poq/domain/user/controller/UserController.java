package com.poq.domain.user.controller;

import com.poq.domain.user.dto.UserLoginRequest;
import com.poq.domain.user.service.UserService;
import com.poq.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UUID>> login(@Valid @RequestBody UserLoginRequest request) {
        UUID userId = userService.loginOrSignUp(request);
        return ResponseEntity.ok(ApiResponse.success(userId));
    }
}