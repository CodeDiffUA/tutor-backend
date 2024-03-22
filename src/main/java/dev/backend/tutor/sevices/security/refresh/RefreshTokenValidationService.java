package dev.backend.tutor.sevices.security.refresh;

import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.entities.auth.RefreshToken;

public interface RefreshTokenValidationService {

    void validateExpiration(RefreshToken refreshToken) throws InvalidTokenException;
}
