package dev.backend.tutor.entities.auth;

import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.utills.student.TokenBuilder;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@MappedSuperclass
public abstract class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    // Common fields for both tokens
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_username", referencedColumnName = "username")
    private Student student;

    private String token;

    private Instant expiryDate;

    // Constructors, getters, and setters
    // Note: Constructor must be protected in a mapped superclass
    protected Token() {
    }

    public Token(Student student, String token, Instant expiryDate) {
        this.student = student;
        this.token = token;
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token token1)) return false;
        return Objects.equals(getId(), token1.getId()) && Objects.equals(getToken(), token1.getToken());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStudent(), getToken(), getExpiryDate());
    }

    // Getters and setters for common fields
    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static TokenBuilder builder() {
        return new TokenBuilder();
    }
}

