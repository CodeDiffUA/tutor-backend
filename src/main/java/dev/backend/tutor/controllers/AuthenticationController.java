package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.authentication.AuthenticationService;
import dev.backend.tutor.sevices.security.updateToken.UpdateTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UpdateTokenService updateTokenService;

    public AuthenticationController(AuthenticationService authenticationService, UpdateTokenService updateTokenService) {
        this.authenticationService = authenticationService;
        this.updateTokenService = updateTokenService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> signIn(
            @RequestBody AuthenticationDtoRequest dtoRequestWithEmail) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException {
        AuthenticationResponseDto response = authenticationService.signIn(dtoRequestWithEmail);
        return ResponseEntity
                .ok()
                .body(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> signInWithRefreshToken(
            @RequestBody UpdateJwtTokenRequest updateJwtTokenRequest) throws NotFoundUserException, InvalidTokenException {
        AuthenticationResponseDto response = updateTokenService.updateRefreshTokenToken(updateJwtTokenRequest);
        return ResponseEntity
                .ok()
                .body(response);
    }
}
