package dev.backend.tutor.services.security.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.repositories.sql.refresh.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RefreshTokenValidationServiceImpl implements RefreshTokenValidationService{

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenValidationServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public void validateExpiration(RefreshToken refreshToken) throws InvalidTokenException {
        if (refreshToken.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidTokenException();
        }
    }
}
