package dev.backend.tutor.utills.student;

import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.entities.confirmationPasswordToken.ConfirmationPasswordToken;

import java.time.Instant;

public class TokenBuilder {

    private String token;
    private Student student;
    private Instant expiryDate;
    public TokenBuilder withToken(String token) {
        this.token = token;
        return this;
    }

    public TokenBuilder withStudent(Student student) {
        this.student = student;
        return this;
    }

    public TokenBuilder withExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }


    public RefreshToken buildRefreshToken() {
        return new RefreshToken(
                student, token, expiryDate
        );
    }

    public ConfirmationEmailToken buildConfirmationToken() {
        return new ConfirmationEmailToken(
                student, token, expiryDate
        );
    }

    public ConfirmationPasswordToken buildConfirmationPasswordToken() {
        return new ConfirmationPasswordToken(
                student, token, expiryDate
        );
    }

}
