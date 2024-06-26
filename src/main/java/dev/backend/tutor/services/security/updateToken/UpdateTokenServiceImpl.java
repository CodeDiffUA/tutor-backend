package dev.backend.tutor.services.security.updateToken;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.sql.refresh.RefreshTokenRepository;
import dev.backend.tutor.services.security.TokenFactory;
import dev.backend.tutor.services.security.refresh.RefreshTokenValidationService;
import org.springframework.stereotype.Service;

@Service
public class UpdateTokenServiceImpl implements UpdateTokenService{

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenFactory tokenFactory;
    private final RefreshTokenValidationService refreshTokenValidationService;

    public UpdateTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, TokenFactory tokenFactory, RefreshTokenValidationService refreshTokenValidationService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenFactory = tokenFactory;
        this.refreshTokenValidationService = refreshTokenValidationService;
    }

    @Override
    public JwtAndRefreshDto updateRefreshTokenToken(UpdateJwtTokenRequest updateJwtTokenRequest) throws InvalidTokenException, NotFoundUserException {
        var refreshToken = getRefreshToken(updateJwtTokenRequest.refreshToken());
        refreshTokenValidationService.validateExpiration(refreshToken);
        Student student = extractStudentFromRefreshToken(refreshToken);
        RefreshToken newRefreshToken = tokenFactory.createRefreshToken(student);
        refreshTokenRepository.delete(refreshToken);
        refreshTokenRepository.insert(newRefreshToken);
        String jwt = tokenFactory.createJwt(student);
        return new JwtAndRefreshDto(jwt, newRefreshToken.getToken());
    }

    private RefreshToken getRefreshToken(String token) throws InvalidTokenException {
        return refreshTokenRepository.findByTokenWithStudentAndHisRoles(token)
                .orElseThrow(InvalidTokenException::new);
    }

    private Student extractStudentFromRefreshToken(RefreshToken refreshToken) {
        return refreshToken.getStudent();
    }

}
