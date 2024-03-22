package dev.backend.tutor.exceptions;

public class WrongPasswordOrUsernameException extends Exception{
    public WrongPasswordOrUsernameException(String message) {
        super(message);
    }
}
