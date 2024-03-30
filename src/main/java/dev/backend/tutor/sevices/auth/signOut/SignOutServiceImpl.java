package dev.backend.tutor.sevices.auth.signOut;

import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.repositories.refresh.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignOutServiceImpl implements SignOutService{

    private final RefreshTokenRepository refreshTokenRepository;

    public SignOutServiceImpl(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }


    @Override
    @Transactional
    public void signOut(String refreshStringToken) throws InvalidTokenException {
        var token = refreshTokenRepository.findRefreshTokenByToken(refreshStringToken)
                        .orElseThrow(() -> new InvalidTokenException("no such a refresh token"));
        refreshTokenRepository.delete(token);
    }
}
