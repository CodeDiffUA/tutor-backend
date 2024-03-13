package dev.backend.tutor.exceptions;

public class NotFoundUserException extends Exception{
    public NotFoundUserException(String message) {
        super(message);
    }
}
