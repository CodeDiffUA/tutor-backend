package dev.backend.tutor.sevices.email;


import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;

public interface EmailSender {

    void sendEmailVerificationMessage(String email, String token) throws NotFoundUserException, InvalidTokenException;

}
