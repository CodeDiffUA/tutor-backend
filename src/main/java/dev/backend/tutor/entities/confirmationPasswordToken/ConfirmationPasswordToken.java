package dev.backend.tutor.entities.confirmationPasswordToken;

import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.entities.auth.Token;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "confirmation_password_tokens")
public class ConfirmationPasswordToken extends Token {

    public ConfirmationPasswordToken(Student student, String token, Instant expiryDate) {
        super(student, token, expiryDate);
    }

    public ConfirmationPasswordToken() {
    }
}

