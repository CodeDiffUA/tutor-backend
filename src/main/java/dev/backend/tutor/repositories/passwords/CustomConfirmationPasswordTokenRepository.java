package dev.backend.tutor.repositories.passwords;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.confirmationPasswordToken.ConfirmationPasswordToken;

import java.util.Optional;

public interface CustomConfirmationPasswordTokenRepository {
    Student findStudentByConfirmationPasswordToken(String token);
    ConfirmationPasswordToken insert(ConfirmationPasswordToken confirmationPasswordToken);

}
