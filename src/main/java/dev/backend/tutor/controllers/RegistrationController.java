package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.CookieException;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.auth.signUp.SignUpService;
import dev.backend.tutor.sevices.auth.signUp.confirm.ConfirmationEmailService;
import dev.backend.tutor.sevices.cookie.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1/registration")
@CrossOrigin(originPatterns = "*")
public class RegistrationController {
    private final SignUpService signUpService;
    private final ConfirmationEmailService confirmationEmailService;
    private final CookieService cookieService;

    public RegistrationController(SignUpService signUpService, ConfirmationEmailService confirmationEmailService, CookieService cookieService) {
        this.signUpService = signUpService;
        this.confirmationEmailService = confirmationEmailService;
        this.cookieService = cookieService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> registerStudent(
            @RequestBody RegistrationDtoRequest registrationDtoRequest,
            HttpServletResponse httpServletResponse
            ) throws AlreadyExistsUserException, NotFoundUserException {
        JwtAndRefreshDto jwtAndRefreshDto = signUpService.registerAccount(registrationDtoRequest);
        Cookie refreshTokenCookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(refreshTokenCookie);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
    }
    @PostMapping("/confirm")
    public ResponseEntity<?> performConfirmation(@Param("token") String token) throws InvalidTokenException {
        confirmationEmailService.confirmEmail(token);
        return ResponseEntity.ok(new RedirectView("localhost:3000/login"));
    }

    private Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        return cookieService.createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
    }
}
