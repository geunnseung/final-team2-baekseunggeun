package com.likelion.sns.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AppException extends RuntimeException {

    private ErrorCode errorCode;
    private String message;
}
