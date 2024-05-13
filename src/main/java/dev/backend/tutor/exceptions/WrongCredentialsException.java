package dev.backend.tutor.exceptions;

public class WrongCredentialsException extends RuntimeException{
    public WrongCredentialsException() {
        super("Wrong password");
    }

    public WrongCredentialsException(String s) {
        super(s);
    }
}
