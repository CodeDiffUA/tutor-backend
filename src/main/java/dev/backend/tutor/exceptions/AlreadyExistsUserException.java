package dev.backend.tutor.exceptions;

public class AlreadyExistsUserException extends RuntimeException{
    public AlreadyExistsUserException(String message) {
        super(message);
    }
}
