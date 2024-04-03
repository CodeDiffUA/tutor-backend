package dev.backend.tutor.entities.auth;

import dev.backend.tutor.entities.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken extends Token{

    public RefreshToken(Student student, String token, Instant expiryDate) {
        super(student, token, expiryDate);
    }

    public RefreshToken() {
    }
}
