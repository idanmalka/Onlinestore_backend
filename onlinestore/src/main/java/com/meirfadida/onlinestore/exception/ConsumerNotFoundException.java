package com.meirfadida.onlinestore.exception;

public class ConsumerNotFoundException extends RuntimeException{
    public ConsumerNotFoundException(String message) {
        super(message);
    }
}
