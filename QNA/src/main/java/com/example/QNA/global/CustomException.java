package com.example.QNA.global;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
    int errorCode;

    public CustomException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
