package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.sevices.student.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public class AuthController {
    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<?> registerStudent(
            @RequestBody RegistrationDtoRequest registrationDtoRequest) {
        registrationService.registerAccount(registrationDtoRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
}
