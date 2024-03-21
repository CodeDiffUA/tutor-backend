package dev.backend.tutor.sevices.email;


public interface EmailSender {

    void sendVerification(String token);

}
