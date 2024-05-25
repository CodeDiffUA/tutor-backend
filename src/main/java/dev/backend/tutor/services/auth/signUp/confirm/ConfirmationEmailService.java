package dev.backend.tutor.services.auth.signUp.confirm;

import dev.backend.tutor.exceptions.InvalidTokenException;

public interface ConfirmationEmailService {

    void confirmEmail(String token) throws InvalidTokenException;
}
