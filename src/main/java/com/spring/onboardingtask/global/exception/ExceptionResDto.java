package com.spring.onboardingtask.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ExceptionResDto {

    private HttpStatus statusCode;
    private String message;
    private String path;
}
