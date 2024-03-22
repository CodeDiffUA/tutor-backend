package dev.backend.tutor.entities.auth;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.utills.student.RefreshTokenBuilder;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String token;
    private Instant expiryDate;
    @ManyToOne
    @JoinColumn(name = "student_username", referencedColumnName = "username")
    private Student student;

    public String getToken() {
        return token;
    }

    public Student getStudent() {
        return student;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public static RefreshTokenBuilder builder() {
        return new RefreshTokenBuilder();
    }

    public RefreshToken() {
    }

    public RefreshToken(String token, Instant expiryDate, Student student) {
        this.token = token;
        this.expiryDate = expiryDate;
        this.student = student;
    }
}
