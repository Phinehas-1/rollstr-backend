package com.bigdecimal.springbootbackend4nextjs.shared.exception;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String message) {
        super(message);
    }
}
