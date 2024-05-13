package dev.backend.tutor.config.security.tokensAuth.factories;

import dev.backend.tutor.config.security.tokensAuth.tokens.RefreshToken;
import org.springframework.security.core.Authentication;

public interface RefreshTokenFactory {
    RefreshToken createToken(Authentication authentication);
}
