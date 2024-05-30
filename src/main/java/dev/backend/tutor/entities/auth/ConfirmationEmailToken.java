package dev.backend.tutor.entities.auth;


import dev.backend.tutor.entities.student.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationEmailToken extends Token{

    public ConfirmationEmailToken(Student student, String token, Instant expiryDate) {
        super(student, token, expiryDate);
    }

    public ConfirmationEmailToken() {
    }
}
