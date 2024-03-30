package dev.backend.tutor.sevices.authentication;

import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.JwtAndRefreshDto;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.exceptions.NotConfirmedEmailException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.sevices.security.jwt.JwtBuilder;
import dev.backend.tutor.sevices.security.refresh.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest {

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private JwtBuilder jwtBuilder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AuthenticationServiceImpl underTest;

    @Test
    void Should_SuccessfullySignInUser() throws UsernameNotFoundException, NotConfirmedEmailException, NotFoundUserException {
        // Arrange
        AuthenticationDtoRequest authenticationDtoRequest = new AuthenticationDtoRequest("testUser", "testPassword");
        UserDetails userDetails = User.withUsername("testUser").password("testPassword").roles("USER").build();
        String jwtToken = "mockJwtToken";
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        JwtAndRefreshDto expectedDto = new JwtAndRefreshDto(jwtToken, refreshToken.getToken());

        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
        when(jwtBuilder.generateJwt(userDetails)).thenReturn(jwtToken);
        when(refreshTokenService.createRefreshToken(userDetails)).thenReturn(refreshToken);

        // Act
        JwtAndRefreshDto resultDto = underTest.signIn(authenticationDtoRequest);

        // Assert
        assertNotNull(resultDto);
        assertEquals(expectedDto.jwt(), resultDto.jwt());
        assertEquals(expectedDto.refreshToken(), resultDto.refreshToken());
        verify(authenticationManager, times(1)).authenticate(any());
        verify(userDetailsService, times(1)).loadUserByUsername("testUser");
        verify(jwtBuilder, times(1)).generateJwt(userDetails);
        verify(refreshTokenService, times(1)).createRefreshToken(userDetails);
    }

    @Test
    void signIn_InvalidCredentials_ThrowsNotConfirmedEmailException() throws UsernameNotFoundException, NotFoundUserException {
        // Arrange
        AuthenticationDtoRequest authenticationDtoRequest = new AuthenticationDtoRequest("testUser", "testPassword");
        doThrow(NotConfirmedEmailException.class).when(authenticationManager).authenticate(any());

        // Act & Assert
        assertThrows(NotConfirmedEmailException.class, () -> underTest.signIn(authenticationDtoRequest));
        verify(jwtBuilder, never()).generateJwt(any());
        verify(refreshTokenService, never()).createRefreshToken(any());
    }

}
