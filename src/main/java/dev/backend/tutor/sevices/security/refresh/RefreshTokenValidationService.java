package dev.backend.tutor.sevices.security.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.InvalidTokenException;

public interface RefreshTokenValidationService {

    void validateExpiration(RefreshToken refreshToken) throws InvalidTokenException;
}
