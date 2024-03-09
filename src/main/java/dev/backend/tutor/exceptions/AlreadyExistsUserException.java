package dev.backend.tutor.exceptions;

public class AlreadyExistsUserException extends Exception{
    public AlreadyExistsUserException(String message) {
        super(message);
    }
}
