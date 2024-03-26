package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.dtos.auth.RegistrationDtoResponse;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.registration.RegistrationService;
import dev.backend.tutor.sevices.registration.confirm.ConfirmationEmailService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/student/registration")
@CrossOrigin("31.144.169.9")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final ConfirmationEmailService confirmationEmailService;

    public RegistrationController(RegistrationService registrationService, ConfirmationEmailService confirmationEmailService) {
        this.registrationService = registrationService;
        this.confirmationEmailService = confirmationEmailService;
    }

    @PostMapping
    public ResponseEntity<RegistrationDtoResponse> registerStudent(
            @RequestBody RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        RegistrationDtoResponse confirmationToken  = registrationService.registerAccount(registrationDtoRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(confirmationToken);
    }
    @GetMapping("/confirm")
    public ResponseEntity<?> performConfirmation(@Param("token") String token) throws InvalidTokenException {
        confirmationEmailService.confirmEmail(token);
        return ResponseEntity.ok().build();
    }
}
