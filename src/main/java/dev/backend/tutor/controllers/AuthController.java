package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.sevices.student.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/student")
@CrossOrigin("http://localhost:3000/")
public class AuthController {
    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> registerStudent(
            @RequestBody RegistrationDtoRequest registrationDtoRequest) throws AlreadyExistsUserException {
        registrationService.registerAccount(registrationDtoRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
