package dev.backend.tutor.config.security.tokensAuth.tokens;

public record Tokens(String accessToken, String accessTokenExpiry,
                     String refreshToken, String refreshTokenExpiry) {
}
