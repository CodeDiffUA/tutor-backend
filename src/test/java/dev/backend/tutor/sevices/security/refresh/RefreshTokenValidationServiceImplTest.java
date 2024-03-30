package dev.backend.tutor.sevices.security.refresh;

import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.repositories.refresh.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefreshTokenValidationServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private RefreshTokenValidationServiceImpl underTest;

    @Test
    void Should_SuccessfullyValidate() throws InvalidTokenException {
        // Arrange
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusSeconds(3600));

        // Act
        underTest.validateExpiration(refreshToken);

        // Assert
        verify(refreshTokenRepository, never()).delete(refreshToken);
    }

    @Test
    void Should_ThrowInvalidTokenException_When_TokenExpired() {
        // Arrange
        RefreshToken expiredToken = new RefreshToken();
        expiredToken.setExpiryDate(Instant.now().minusSeconds(3600));

        // Act & Assert
        assertThrows(InvalidTokenException.class, () -> underTest.validateExpiration(expiredToken));
        verify(refreshTokenRepository, times(1)).delete(expiredToken);
    }
}
