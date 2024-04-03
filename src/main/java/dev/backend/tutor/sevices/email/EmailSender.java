package dev.backend.tutor.sevices.email;


import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;

import java.io.IOException;

public interface EmailSender {

    void sendEmailVerificationMessage(String email) throws NotFoundUserException, InvalidTokenException, IOException;
    void sendEmailForgotPasswordMessage(String email) throws NotFoundUserException, InvalidTokenException, IOException;

}
