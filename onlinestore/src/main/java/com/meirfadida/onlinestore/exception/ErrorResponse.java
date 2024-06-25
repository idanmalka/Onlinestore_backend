package com.meirfadida.onlinestore.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String details;
}

