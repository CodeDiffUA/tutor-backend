package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.CookieException;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.auth.signIn.SignInService;
import dev.backend.tutor.sevices.auth.signOut.SignOutService;
import dev.backend.tutor.sevices.security.updateToken.UpdateTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin(originPatterns = "*")
public class AuthController {

    private static final Integer REFRESH_COOKIE_LIVE_TERM = 3600*24*14;

    private final SignInService signInService;
    private final UpdateTokenService updateTokenService;
    private final SignOutService signOutService;

    public AuthController(SignInService signInService, UpdateTokenService updateTokenService, SignOutService signOutService) {
        this.signInService = signInService;
        this.updateTokenService = updateTokenService;
        this.signOutService = signOutService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> signIn(
            @RequestBody AuthenticationDtoRequest dtoRequestWithEmail, HttpServletResponse httpServletResponse) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException {
        JwtAndRefreshDto jwtAndRefreshDto = signInService.signIn(dtoRequestWithEmail);
        var cookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity
                .ok()
                .body(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthenticationResponseDto> signOut(
            HttpServletRequest httpServletRequest) throws UsernameNotFoundException, NotConfirmedEmailException, InvalidTokenException, CookieException {
        var refreshCookie = getRefreshTokenCookie(httpServletRequest);
        var refreshStringToken = refreshCookie.getValue();
        signOutService.signOut(refreshStringToken);
        refreshCookie.setMaxAge(0);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> updateAccess(
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws NotFoundUserException, InvalidTokenException, CookieException {
        Cookie refreshTokenCookie = getRefreshTokenCookie(httpServletRequest);
        UpdateJwtTokenRequest updateJwtTokenRequest = new UpdateJwtTokenRequest(refreshTokenCookie.getValue());
        JwtAndRefreshDto jwtAndRefreshDto = updateTokenService.updateRefreshTokenToken(updateJwtTokenRequest);
        var cookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity
                .ok()
                .body(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
    }

    private Cookie getRefreshTokenCookie(HttpServletRequest httpServletRequest) throws CookieException {
        return Arrays.stream(httpServletRequest.getCookies())
                .filter(cookie -> cookie.getName().equals("refreshToken"))
                .findFirst()
                .orElseThrow(() -> new CookieException("no refresh cookie"));
    }

    private Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        Cookie cookie = new Cookie("refreshToken", jwtAndRefreshDto.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(REFRESH_COOKIE_LIVE_TERM);
        httpServletResponse.setContentType("text/plain");
        httpServletResponse.addCookie(cookie);
        return cookie;
    }
}
