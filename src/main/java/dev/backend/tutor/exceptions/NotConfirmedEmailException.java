package dev.backend.tutor.exceptions;

public class NotConfirmedEmailException extends RuntimeException{
    public NotConfirmedEmailException(String message) {
        super(message);
    }
}
