package com.spring.onboardingtask.user.eums;

import com.spring.onboardingtask.user.entity.Authority;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    USER,
    ADMIN;

    public Authority toAuthority() {
        return Authority.builder()
            .authorityName("ROLE_" + this.name())
            .build();
    }
}
