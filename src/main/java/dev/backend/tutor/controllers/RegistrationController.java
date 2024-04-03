package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.auth.signUp.SignUpService;
import dev.backend.tutor.sevices.auth.signUp.confirm.ConfirmationEmailService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@CrossOrigin(originPatterns = "*")
public class RegistrationController {
    private final SignUpService signUpService;
    private final ConfirmationEmailService confirmationEmailService;

    public RegistrationController(SignUpService signUpService, ConfirmationEmailService confirmationEmailService) {
        this.signUpService = signUpService;
        this.confirmationEmailService = confirmationEmailService;
    }

    @PostMapping
    public ResponseEntity<?> registerStudent(
            @RequestBody RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException, NotFoundUserException {
        signUpService.registerAccount(registrationDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("/confirm")
    public ResponseEntity<?> performConfirmation(@Param("token") String token) throws InvalidTokenException {
        confirmationEmailService.confirmEmail(token);
        return ResponseEntity.ok().build();
    }
}
