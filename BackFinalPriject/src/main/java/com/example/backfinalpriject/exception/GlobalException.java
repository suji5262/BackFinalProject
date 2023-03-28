package com.example.backfinalpriject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GlobalException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;
}
