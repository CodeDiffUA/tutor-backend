package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.services.auth.signUp.SignUpService;
import dev.backend.tutor.services.auth.signUp.confirm.ConfirmationEmailServiceImpl;
import dev.backend.tutor.services.cookie.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1/registration")
@CrossOrigin(originPatterns = "*")
public class RegistrationController {
    private final SignUpService signUpService;
    private final ConfirmationEmailServiceImpl confirmationEmailService;
    private final CookieService cookieService;

    @Value("${front-end.base-url}")
    private String frontUrl;

    public RegistrationController(SignUpService signUpService, ConfirmationEmailServiceImpl confirmationEmailService, CookieService cookieService) {
        this.signUpService = signUpService;
        this.confirmationEmailService = confirmationEmailService;
        this.cookieService = cookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> registerStudent(
            @RequestBody RegistrationDtoRequest registrationDtoRequest,
            HttpServletResponse httpServletResponse
            ) throws AlreadyExistsUserException, NotFoundUserException {
        JwtAndRefreshDto jwtAndRefreshDto = signUpService.registerAccountWithLogin(registrationDtoRequest);
        Cookie refreshTokenCookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(refreshTokenCookie);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> registerStudentWithLogin(
            @RequestBody RegistrationDtoRequest registrationDtoRequest
    ) throws AlreadyExistsUserException, NotFoundUserException {
        signUpService.registerAccount(registrationDtoRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("/confirm")
    public RedirectView performConfirmation(@RequestParam("token") String token) throws InvalidTokenException {
        confirmationEmailService.confirmEmail(token);
        return new RedirectView(frontUrl+"/login");
    }

    @GetMapping("/confirm-login")
    public RedirectView performConfirmationAndLogin(
            @RequestParam("token") String token,
            HttpServletResponse httpServletResponse
            ) throws InvalidTokenException, NotFoundUserException {
        JwtAndRefreshDto jwtAndRefreshDto = confirmationEmailService.confirmEmailAndLogin(token);
        Cookie refreshTokenCookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(refreshTokenCookie);
        return new RedirectView(frontUrl+"?accessToken="+jwtAndRefreshDto.jwt());
    }

    private Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        return cookieService.createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
    }
}
