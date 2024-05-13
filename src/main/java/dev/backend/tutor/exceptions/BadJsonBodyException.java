package dev.backend.tutor.exceptions;

public class BadJsonBodyException extends RuntimeException{
    public BadJsonBodyException(String message) {
        super(message);
    }
}
