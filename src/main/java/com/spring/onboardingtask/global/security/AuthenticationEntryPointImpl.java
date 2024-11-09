package com.spring.onboardingtask.global.security;

import com.spring.onboardingtask.global.exception.ExceptionResDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j(topic = "AuthenticationEntryPointImpl")
@Component
@RequiredArgsConstructor
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException)
        throws IOException {

        boolean jwtError = request.getAttribute("error") != null;
        log.debug("jwtError : {}", jwtError);
        String error = jwtError ? request.getAttribute("error").toString()
            : HttpStatus.UNAUTHORIZED.getReasonPhrase();
        log.debug("error : {}", error);
        String message = jwtError ? error : "인증되지 않은 사용자입니다.";
        log.debug("error message : {}", message);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(
            ExceptionResDto.builder()
                .statusCode(HttpStatus.UNAUTHORIZED)
                .message(message)
                .path(request.getRequestURI())
                .build().toString()
        );
    }
}
