package dev.backend.tutor.dtos.auth;

public record AuthenticationResponseDto(
        String jwt, String refreshToken
) {
}
