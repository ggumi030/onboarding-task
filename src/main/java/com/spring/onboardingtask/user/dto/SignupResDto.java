package com.spring.onboardingtask.user.dto;

import com.spring.onboardingtask.user.entity.Authority;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResDto {
    private String username;
    private String nickname;
    private List<AuthDto> authorities;
}
