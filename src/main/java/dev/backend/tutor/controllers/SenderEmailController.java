package dev.backend.tutor.controllers;

import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.email.EmailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "*")
public class SenderEmailController {

    private final EmailSender emailSender;

    public SenderEmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/send-verification")
    public ResponseEntity<?> sendConfirmationEmailMessage(
            @RequestParam("email") String email) throws NotFoundUserException, InvalidTokenException, IOException {
        emailSender.sendEmailVerificationMessage(email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> sendForgotPasswordEmail(
            @RequestParam("email") String email) throws NotFoundUserException, InvalidTokenException, IOException {
        emailSender.sendEmailForgotPasswordMessage(email);
        return ResponseEntity.ok().build();
    }
}
