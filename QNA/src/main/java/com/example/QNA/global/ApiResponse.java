package com.example.QNA.global;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
//빌드업패턴
public class ApiResponse<T> {
    private int statusCode;
    private String statusMessage;
    private T data;

    public ApiResponse(int statusCode, String statusMessage, T data) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.data = data;
    }

    public ApiResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

}
