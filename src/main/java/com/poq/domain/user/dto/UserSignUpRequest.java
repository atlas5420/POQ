package com.poq.domain.user.dto;

import com.poq.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSignUpRequest {
    
    private String username;
    private String password;
    private String name;
    private String email;

    // DTO 데이터를 바탕으로 실제 DB에 저장할 Entity 객체로 변환하는 헬퍼 메소드입니다.
    public User toEntity() {
        return new User(this.username, this.password, this.name, this.email);
    }
}