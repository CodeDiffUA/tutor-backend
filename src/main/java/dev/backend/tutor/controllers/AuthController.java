package dev.backend.tutor.controllers;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.AuthenticationResponseDto;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.*;
import dev.backend.tutor.sevices.auth.oath2.Oath2GoogleService;
import dev.backend.tutor.sevices.auth.signIn.SignInService;
import dev.backend.tutor.sevices.auth.signOut.SignOutService;
import dev.backend.tutor.sevices.cookie.CookieService;
import dev.backend.tutor.sevices.security.updateToken.UpdateTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1/authentication")
@CrossOrigin(originPatterns = "*")
public class AuthController {

    private final SignInService signInService;
    private final UpdateTokenService updateTokenService;
    private final SignOutService signOutService;
    private final Oath2GoogleService oath2GoogleService;
    private final CookieService cookieService;


    public AuthController(SignInService signInService, UpdateTokenService updateTokenService, SignOutService signOutService, Oath2GoogleService oath2GoogleService, CookieService cookieService) {
        this.signInService = signInService;
        this.updateTokenService = updateTokenService;
        this.signOutService = signOutService;
        this.oath2GoogleService = oath2GoogleService;
        this.cookieService = cookieService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> signIn(
            @RequestBody AuthenticationDtoRequest dtoRequestWithEmail, HttpServletResponse httpServletResponse) throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException, WrongCredentialsException {
        JwtAndRefreshDto jwtAndRefreshDto = signInService.signIn(dtoRequestWithEmail);
        var cookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity
                .ok()
                .body(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
    }

    @GetMapping("/google-login")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public RedirectView googleSignIn() throws IOException, NotFoundUserException {
        return new RedirectView(oath2GoogleService.getGoogleAuthorizationRedirectUrl());
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(
            @RequestParam("code") String code,
            HttpServletResponse httpServletResponse) throws BannedException, NotFoundUserException {
        JwtAndRefreshDto jwtAndRefreshDto;
        try {
            jwtAndRefreshDto = oath2GoogleService.authorizeStudent(code);
        } catch (IOException exception) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("could not authenticate with google");
        }
        var cookie = createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
        httpServletResponse.addCookie(cookie);
        return ResponseEntity.ok(new AuthenticationResponseDto(jwtAndRefreshDto.jwt()));
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
        return cookieService.getRefreshTokenCookie(httpServletRequest);
    }

    private Cookie createCookieWithRefreshToken(HttpServletResponse httpServletResponse, JwtAndRefreshDto jwtAndRefreshDto) {
        return cookieService.createCookieWithRefreshToken(httpServletResponse, jwtAndRefreshDto);
    }
}
