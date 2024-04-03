package dev.backend.tutor.controllers;

import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.email.EmailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(originPatterns = "*")
public class SenderEmailController {

    private final EmailSender emailSender;

    public SenderEmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @GetMapping("/send-verification")
    public ResponseEntity<?> sendConfirmationEmailMessage(
            @RequestParam("email") String email,
            @RequestParam("token") String token) throws NotFoundUserException, InvalidTokenException {
        emailSender.sendEmailVerificationMessage(email, token);
        return ResponseEntity.ok().build();
    }
}
