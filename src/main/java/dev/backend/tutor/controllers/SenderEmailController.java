package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.email.ConfirmationRequest;
import dev.backend.tutor.sevices.email.EmailSender;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<?> sendConfirmationEmailMessage(@RequestBody ConfirmationRequest confirmationRequest) {
        emailSender.sendEmailVerificationMessage(confirmationRequest.email(), confirmationRequest.token());
        return ResponseEntity.ok().build();
    }
}
