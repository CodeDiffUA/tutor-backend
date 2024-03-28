package dev.backend.tutor.sevices.security.refresh;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.entities.auth.Role;
import dev.backend.tutor.entities.auth.UserRole;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenFactoryTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private TokenFactory tokenFactory;

    @Test
    void Should_CreateRefreshToken() throws NotFoundUserException {
        // Arrange
        var student = generateStudent();
        UserDetails userDetails = User.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .authorities(student.getRoles())
                .build();
        when(studentRepository.findStudentByUsername(student.getUsername())).thenReturn(Optional.of(student));
        when(jwtUtil.refreshLifeTime()).thenReturn(3600000L); // 1 hour

        // Act
        RefreshToken refreshToken = tokenFactory.createRefreshToken(userDetails);

        // Assert
        assertNotNull(refreshToken);
        assertEquals(student.getUsername(), refreshToken.getStudent().getUsername());
        assertFalse(refreshToken.getToken().isEmpty());
        assertTrue(refreshToken.getExpiryDate().isAfter(Instant.now()));
        assertTrue(refreshToken.getExpiryDate().isBefore(Instant.now().plusSeconds(3600))); // 1 hour expiry
        verify(studentRepository, times(1)).findStudentByUsername(student.getUsername());
    }

    @Test
    void Should_TrowNotFoundUserException_When_UserNotFound() {
        // Arrange
        var student = generateStudent();
        UserDetails userDetails = User.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .authorities(student.getRoles())
                .build();
        when(studentRepository.findStudentByUsername(student.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundUserException.class, () -> tokenFactory.createRefreshToken(userDetails));
        verify(studentRepository, times(1)).findStudentByUsername(student.getUsername());
    }

    @Test
    void Should_CreateSuccessfullyConfirmationToken() throws NotFoundUserException {
        // Arrange
        var student = generateStudent();
        UserDetails userDetails = User.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .authorities(student.getRoles())
                .build();
        when(studentRepository.findStudentByUsername(student.getUsername())).thenReturn(Optional.of(student));

        // Act
        ConfirmationEmailToken confirmationEmailToken = tokenFactory.createConfirmationEmailToken(userDetails);

        // Assert
        assertNotNull(confirmationEmailToken);
        assertEquals(student.getUsername(), confirmationEmailToken.getStudent().getUsername());
        assertFalse(confirmationEmailToken.getToken().isEmpty());
        assertTrue(confirmationEmailToken.getExpiryDate().isAfter(Instant.now()));
        assertTrue(confirmationEmailToken.getExpiryDate().isBefore(Instant.now().plusSeconds(3600 * 24))); // 24 hours expiry
        verify(studentRepository, times(1)).findStudentByUsername(student.getUsername());
    }

    @Test
    void Should_TrowNotFoundUserException_When_UserDetailsWrong() {
        // Arrange
        var student = generateStudent();
        UserDetails userDetails = User.builder()
                .username(student.getUsername())
                .password(student.getPassword())
                .authorities(student.getRoles())
                .build();
        when(studentRepository.findStudentByUsername(student.getUsername())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundUserException.class, () -> tokenFactory.createConfirmationEmailToken(userDetails));
        verify(studentRepository, times(1)).findStudentByUsername(student.getUsername());
    }

    private Student generateStudent(){
        String username = "testUser";
        Student student = new Student();
        student.setUsername(username);
        student.addRole(new UserRole(student, Role.ROLE_STUDENT));
        student.setPassword("testUser");
        return student;
    }
}
