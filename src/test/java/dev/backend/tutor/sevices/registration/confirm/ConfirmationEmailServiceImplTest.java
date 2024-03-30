package dev.backend.tutor.sevices.registration.confirm;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.exceptions.InvalidTokenException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ConfirmationEmailServiceImplTest {

    @Mock
    private ConfirmationEmailTokenRepository confirmationEmailTokenRepository;

    @InjectMocks
    private ConfirmationEmailServiceImpl underTest;

    @Test
    void Should_TrowInvalidTokenException_When_Token_NotExistInDB() {
        // Arrange
        String token = "someToken";
        when(confirmationEmailTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(InvalidTokenException.class, () -> underTest.confirmEmail(token));

        // Verify
        verify(confirmationEmailTokenRepository, times(1)).findByToken(token);
        verifyNoMoreInteractions(confirmationEmailTokenRepository);
    }

    @Test
    void Should_TrowInvalidTokenException_When_Token_Expired() {
        // Arrange
        String token = "someToken";
        ConfirmationEmailToken expiredToken = new ConfirmationEmailToken();
        expiredToken.setExpiryDate(Instant.now().minusSeconds(3600));
        when(confirmationEmailTokenRepository.findByToken(token)).thenReturn(Optional.of(expiredToken));

        // Act & Assert
        assertThrows(InvalidTokenException.class, () -> underTest.confirmEmail(token));

        // Verify
        verify(confirmationEmailTokenRepository, times(1)).findByToken(token);
        verifyNoMoreInteractions(confirmationEmailTokenRepository);
    }

    @Test
    void Should_SuccessfullyConfirmEmail_When_TokenIsValid() throws InvalidTokenException {
        // Arrange
        String token = "someToken";
        ConfirmationEmailToken validToken = new ConfirmationEmailToken();
        validToken.setExpiryDate(Instant.now().plusSeconds(3600));
        Student student = new Student();
        validToken.setStudent(student);
        when(confirmationEmailTokenRepository.findByToken(token)).thenReturn(Optional.of(validToken));

        // Act
        underTest.confirmEmail(token);

        // Assert
        assertTrue(student.isEnabled());
        verify(confirmationEmailTokenRepository, times(1)).findByToken(token);
        verify(confirmationEmailTokenRepository, times(1)).delete(validToken);
        verifyNoMoreInteractions(confirmationEmailTokenRepository);
    }
}
