package dev.backend.tutor.sevices.security.updateToken;

import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.dtos.auth.UpdateJwtTokenRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.entities.auth.Role;
import dev.backend.tutor.entities.auth.UserRole;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.refresh.RefreshTokenRepository;
import dev.backend.tutor.sevices.security.jwt.JwtBuilder;
import dev.backend.tutor.sevices.security.refresh.RefreshTokenValidationService;
import dev.backend.tutor.sevices.security.refresh.TokenFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateTokenServiceImplTest {

    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @Mock
    private TokenFactory tokenFactory;

    @Mock
    private RefreshTokenValidationService refreshTokenValidationService;

    @Mock
    private JwtBuilder jwtBuilder;

    @InjectMocks
    private UpdateTokenServiceImpl underTest;

    @Test
    void updateRefreshTokenToken_ValidRequest_ReturnsJwtAndRefreshDto() throws InvalidTokenException, NotFoundUserException {
        // Arrange
        String refreshTokenValue = "refreshTokenValue";
        UpdateJwtTokenRequest updateJwtTokenRequest = new UpdateJwtTokenRequest(refreshTokenValue);
        RefreshToken oldRefreshToken = new RefreshToken();
        oldRefreshToken.setToken(refreshTokenValue);
        Student student = new Student();
        student.setUsername("testUser");
        student.setPassword("testPassword");
        student.addRole(new UserRole(student, Role.ROLE_STUDENT));
        oldRefreshToken.setStudent(student);
        UserDetails userDetails = new User(student.getUsername(), student.getPassword(), student.getRoles());
        RefreshToken newRefreshToken = new RefreshToken();
        newRefreshToken.setToken("newRefreshTokenValue");
        JwtAndRefreshDto expectedDto = new JwtAndRefreshDto("newJwtToken", refreshTokenValue);

        when(refreshTokenRepository.findByTokenWithStudentAndHisRoles(refreshTokenValue)).thenReturn(java.util.Optional.of(oldRefreshToken));
        when(tokenFactory.createRefreshToken(userDetails)).thenReturn(newRefreshToken);
        when(jwtBuilder.generateJwt(userDetails)).thenReturn("newJwtToken");

        // Act
        JwtAndRefreshDto resultDto = underTest.updateRefreshTokenToken(updateJwtTokenRequest);

        // Assert
        assertNotNull(resultDto);
        assertEquals(expectedDto.jwt(), resultDto.jwt());
        assertEquals(expectedDto.refreshToken(), resultDto.refreshToken());
        verify(refreshTokenValidationService, times(1)).validateExpiration(oldRefreshToken);
        verify(refreshTokenRepository, times(1)).delete(oldRefreshToken);
        verify(refreshTokenRepository, times(1)).save(newRefreshToken);
    }

    @Test
    void updateRefreshTokenToken_InvalidToken_ThrowsInvalidTokenException() throws InvalidTokenException {
        // Arrange
        String invalidRefreshTokenValue = "invalidRefreshTokenValue";
        UpdateJwtTokenRequest updateJwtTokenRequest = new UpdateJwtTokenRequest(invalidRefreshTokenValue);

        when(refreshTokenRepository.findByTokenWithStudentAndHisRoles(invalidRefreshTokenValue)).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(InvalidTokenException.class, () -> underTest.updateRefreshTokenToken(updateJwtTokenRequest));
        verify(refreshTokenValidationService, never()).validateExpiration(any());
        verify(refreshTokenRepository, never()).delete(any());
        verify(refreshTokenRepository, never()).save(any());
    }
}
