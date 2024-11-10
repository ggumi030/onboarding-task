package com.spring.onboardingtask.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupReqDto {

    @NotNull(message = "username 값이 필수로 들어있어야 합니다.")
    private String username;
    @NotNull(message = "password 값이 필수로 들어있어야 합니다.")
    private String password;
    @NotNull(message = "nickname 값이 필수로 들어있어야 합니다.")
    private String nickname;
}
