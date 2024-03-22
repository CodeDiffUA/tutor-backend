package dev.backend.tutor.utills.student;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.RefreshToken;

import java.time.Instant;

public class RefreshTokenBuilder {

    private String token;
    private Student student;
    private Instant expiryDate;
    public RefreshTokenBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public RefreshTokenBuilder withStudent(Student student) {
        this.student = student;
        return this;
    }

    public RefreshTokenBuilder withExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }


    public RefreshToken build() {
        return new RefreshToken(
                token, expiryDate, student
        );
    }

}
