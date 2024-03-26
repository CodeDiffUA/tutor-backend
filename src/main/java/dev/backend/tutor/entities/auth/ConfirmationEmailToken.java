package dev.backend.tutor.entities.auth;


import dev.backend.tutor.entities.Student;
import dev.backend.tutor.utills.student.TokenBuilder;
import jakarta.persistence.*;

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
