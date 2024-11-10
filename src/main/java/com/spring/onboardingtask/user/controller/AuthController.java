package com.spring.onboardingtask.user.controller;

import com.spring.onboardingtask.user.dto.SignupReqDto;
import com.spring.onboardingtask.user.dto.SignupResDto;
import com.spring.onboardingtask.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResDto> signup(@Valid @RequestBody SignupReqDto signupReqDto) {
        SignupResDto resDto = authService.signup(signupReqDto);
        return ResponseEntity.ok().body(resDto);
    }

}
