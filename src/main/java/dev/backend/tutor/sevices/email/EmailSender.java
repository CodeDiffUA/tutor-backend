package dev.backend.tutor.sevices.email;


public interface EmailSender {

    void sendEmailVerificationMessage(String email, String token);

}
