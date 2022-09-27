package com.mrugesh1996.waraclecoding.exceptionhandling;

public class CakeNotFoundException extends RuntimeException{
    public CakeNotFoundException(String message) {
        super(message);
    }

    public CakeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CakeNotFoundException(Throwable cause) {
        super(cause);
    }
}
