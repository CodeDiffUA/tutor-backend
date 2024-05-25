package dev.backend.tutor.repositories.sql.passwords;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.confirmationPasswordToken.ConfirmationPasswordToken;

public interface CustomConfirmationPasswordTokenRepository {
    Student findStudentByConfirmationPasswordToken(String token);
    ConfirmationPasswordToken insert(ConfirmationPasswordToken confirmationPasswordToken);

}
