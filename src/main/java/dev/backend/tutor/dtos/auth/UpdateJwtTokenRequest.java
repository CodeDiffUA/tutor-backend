package dev.backend.tutor.dtos.auth;

public record UpdateJwtTokenRequest(
        String refreshToken
) {
}
