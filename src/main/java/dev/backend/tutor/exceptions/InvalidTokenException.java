package dev.backend.tutor.exceptions;

public class InvalidTokenException extends Exception{
    public InvalidTokenException() {
        super("Invalid token");
    }

    public InvalidTokenException(String message) {
        super(message);
    }
}
