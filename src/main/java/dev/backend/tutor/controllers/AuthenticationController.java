package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.authentication.AuthenticationService;
import dev.backend.tutor.sevices.security.updateToken.UpdateTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin(originPatterns = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UpdateTokenService updateTokenService;

    public AuthenticationController(AuthenticationService authenticationService, UpdateTokenService updateTokenService) {
        this.authenticationService = authenticationService;
        this.updateTokenService = updateTokenService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationResponseDto> signIn(
            @RequestBody AuthenticationDtoRequest dtoRequestWithEmail, HttpServletResponse httpServletResponse) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException {
        JwtAndRefreshDto jwtAndRefreshDto = authenticationService.signIn(dtoRequestWithEmail);
        var cookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity
                .ok()
                .body(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> signInWithRefreshToken(
            @RequestBody UpdateJwtTokenRequest updateJwtTokenRequest, HttpServletResponse httpServletResponse) throws NotFoundUserException, InvalidTokenException {
        JwtAndRefreshDto jwtAndRefreshDto = updateTokenService.updateRefreshTokenToken(updateJwtTokenRequest);
        var cookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity
                .ok()
                .body(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
    }

    private Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        Cookie cookie = new Cookie("refreshToken", jwtAndRefreshDto.refreshToken());
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        httpServletResponse.setContentType("text/plain");
        httpServletResponse.addCookie(cookie);
        return cookie;
    }
}
