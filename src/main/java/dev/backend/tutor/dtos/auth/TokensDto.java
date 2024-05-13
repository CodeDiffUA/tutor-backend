package dev.backend.tutor.dtos.auth;

public record TokensDto(String accessToken, String accessTokenExpiry,
                        String refreshToken, String refreshTokenExpiry) {
}
