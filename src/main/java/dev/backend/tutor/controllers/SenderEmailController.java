package dev.backend.tutor.controllers;

import dev.backend.tutor.sevices.email.EmailSender;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class SenderEmailController {

    private final EmailSender emailSender;

    public SenderEmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/send-verification")
    public ResponseEntity<?> sendConfirmationEmailMessage(
            @Param("email") String email,
            @Param("token") String token) {
        emailSender.sendEmailVerificationMessage(email, token);
        return ResponseEntity.ok().build();
    }
}
