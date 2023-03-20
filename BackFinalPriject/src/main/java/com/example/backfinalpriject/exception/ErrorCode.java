package com.example.backfinalpriject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, ""),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, ""),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, ""),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, ""),
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, ""),
    NOT_LOGIN(HttpStatus.UNAUTHORIZED, "");

    private HttpStatus httpStatus;
    private String message;
}
