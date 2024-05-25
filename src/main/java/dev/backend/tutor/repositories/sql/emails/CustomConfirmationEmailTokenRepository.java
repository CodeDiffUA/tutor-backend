package dev.backend.tutor.repositories.sql.emails;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;

public interface CustomConfirmationEmailTokenRepository {

    ConfirmationEmailToken insert(ConfirmationEmailToken confirmationEmailToken);
}
