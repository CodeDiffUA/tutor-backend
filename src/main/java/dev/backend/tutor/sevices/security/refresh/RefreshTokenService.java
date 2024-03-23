package dev.backend.tutor.sevices.security.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.refresh.RefreshTokenRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class RefreshTokenService {

    private final TokenFactory tokenFactory;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(TokenFactory tokenFactory, RefreshTokenRepository refreshTokenRepository) {
        this.tokenFactory = tokenFactory;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(@NonNull UserDetails userDetails) throws NotFoundUserException {
        var refreshToken = tokenFactory.createRefreshToken(userDetails);
        return refreshTokenRepository.save(refreshToken);
    }

}
