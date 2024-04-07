package dev.backend.tutor.repositories.passwords;

import dev.backend.tutor.entities.confirmationPasswordToken.ConfirmationPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfirmationPasswordTokenRepository extends JpaRepository<ConfirmationPasswordToken, Long>, CustomConfirmationPasswordTokenRepository{
}
