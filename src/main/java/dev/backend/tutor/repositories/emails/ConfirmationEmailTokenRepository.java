package dev.backend.tutor.repositories.emails;

import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationEmailTokenRepository extends JpaRepository<ConfirmationEmailToken, Long> {

    Optional<ConfirmationEmailToken> findByToken(String token);
}
