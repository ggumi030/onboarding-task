package com.spring.onboardingtask.user.mapper;

import com.spring.onboardingtask.user.dto.AuthDto;
import com.spring.onboardingtask.user.dto.SignupResDto;
import com.spring.onboardingtask.user.entity.User;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public class UserMapper {
    public static SignupResDto toSignupResDto(User entity) {
        return SignupResDto.builder()
            .username(entity.getUsername())
            .nickname(entity.getNickname())
            .authorities(entity.getAuthorities().stream()
                .map(authority -> AuthDto.builder()
                    .authorityName(authority.getAuthorityName())
                    .build())
                .toList())
            .build();
    }
}
