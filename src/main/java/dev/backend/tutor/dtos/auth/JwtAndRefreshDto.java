package dev.backend.tutor.dtos.auth;

public record JwtAndRefreshDto(
        String jwt, String refreshToken
) {
}
