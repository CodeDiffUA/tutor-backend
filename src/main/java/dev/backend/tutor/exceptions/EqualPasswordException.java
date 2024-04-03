package dev.backend.tutor.exceptions;

public class EqualPasswordException extends Exception {
    public EqualPasswordException (String message){
        super(message);
    }
}
