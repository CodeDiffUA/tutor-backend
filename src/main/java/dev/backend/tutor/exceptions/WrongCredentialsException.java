package dev.backend.tutor.exceptions;

public class WrongCredentialsException extends Exception{
    public WrongCredentialsException() {
        super("Wrong password");
    }

    public WrongCredentialsException(String s) {
        super(s);
    }
}
