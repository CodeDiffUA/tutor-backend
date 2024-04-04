package dev.backend.tutor.repositories.emails;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;

public interface CustomConfirmationEmailTokenRepository {

    ConfirmationEmailToken insert(ConfirmationEmailToken confirmationEmailToken);
}
