package com.meirfadida.onlinestore.exception;

public class EmailAlreadyExitsException extends RuntimeException{
    public EmailAlreadyExitsException(String message) {
        super(message);
    }
}
