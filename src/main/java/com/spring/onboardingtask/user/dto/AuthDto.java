package com.spring.onboardingtask.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthDto {
    private String authorityName;
}
