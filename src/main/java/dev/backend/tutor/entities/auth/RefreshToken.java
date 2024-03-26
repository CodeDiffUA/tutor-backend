package dev.backend.tutor.entities.auth;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.utills.student.TokenBuilder;
import jakarta.persistence.*;

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
