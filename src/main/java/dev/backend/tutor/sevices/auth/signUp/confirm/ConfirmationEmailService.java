package dev.backend.tutor.sevices.auth.signUp.confirm;

import dev.backend.tutor.exceptions.InvalidTokenException;

public interface ConfirmationEmailService {

    void confirmEmail(String token) throws InvalidTokenException;
}
