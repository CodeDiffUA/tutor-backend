package dev.backend.tutor.exceptions.security;

public class BadAuthHeaderException extends RuntimeException {
    public BadAuthHeaderException() {
        super("Cannot properly read authorization header");
    }

    public BadAuthHeaderException(String message) {
        super(message);
    }
}
