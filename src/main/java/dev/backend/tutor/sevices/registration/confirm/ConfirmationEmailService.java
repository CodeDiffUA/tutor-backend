package dev.backend.tutor.sevices.registration.confirm;

import dev.backend.tutor.exceptions.InvalidTokenException;

public interface ConfirmationEmailService {

    void confirmEmail(String token) throws InvalidTokenException;
}
