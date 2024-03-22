package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.exceptions.WrongPasswordOrUsernameException;
import dev.backend.tutor.sevices.authentication.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> registerStudent(
            @RequestBody AuthenticationDtoRequest dtoRequestWithEmail) throws WrongPasswordOrUsernameException, NotFoundUserException {
        AuthenticationResponseDto response = authenticationService.signIn(dtoRequestWithEmail);
        return ResponseEntity
                .ok()
                .body(response);
    }
}
