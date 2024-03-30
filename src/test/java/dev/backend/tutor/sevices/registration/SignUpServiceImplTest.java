package dev.backend.tutor.sevices.registration;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.ConfirmationEmailToken;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.auth.signUp.SignUpServiceImpl;
import dev.backend.tutor.sevices.auth.signUp.validation.StudentValidationService;
import dev.backend.tutor.sevices.security.refresh.TokenFactory;
import dev.backend.tutor.utills.student.Form;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SignUpServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentValidationService validationService;

    @Mock
    private TokenFactory tokenFactory;

    @Mock
    private ConfirmationEmailTokenRepository confirmationEmailTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignUpServiceImpl underTest;

    @Test
    void Should_RegisterAccountSuccessful_When_RequestValid() throws AlreadyExistsUserException, NotFoundUserException {
        // arrange
        RegistrationDtoRequest registrationDtoRequest = new RegistrationDtoRequest(
                "testuser", "test@example.com", "password", Form.ELEVENTH, 16
        );

        doNothing().when(validationService).validateEmail(registrationDtoRequest.email());
        doNothing().when(validationService).validateUsername(registrationDtoRequest.username());
        when(passwordEncoder.encode(registrationDtoRequest.password())).thenReturn(registrationDtoRequest.password());
        when(tokenFactory.createConfirmationEmailToken(any(User.class))).thenReturn(new ConfirmationEmailToken());

        // when
        assertDoesNotThrow(() -> underTest.registerAccount(registrationDtoRequest));

        // then
        verify(studentRepository, times(1)).save(any(Student.class));
        verify(confirmationEmailTokenRepository, times(1)).save(any(ConfirmationEmailToken.class));
    }

    @Test
    void Should_FailRegistration_When_UsernameDuplicated() throws AlreadyExistsUserException {
        // arrange
        RegistrationDtoRequest registrationDtoRequest = new RegistrationDtoRequest(
                "testuser", "test@example.com", "password", Form.ELEVENTH, 16
        );
        doThrow(AlreadyExistsUserException.class).when(validationService).validateEmail(registrationDtoRequest.email());

        // when
        assertThrows(AlreadyExistsUserException.class, () -> underTest.registerAccount(registrationDtoRequest));

        // then
        verify(validationService).validateEmail(registrationDtoRequest.email());
        verify(studentRepository, never()).save(any());
        verify(confirmationEmailTokenRepository, never()).save(any());
    }

    @Test
    void Should_FailRegistration_When_EmailDuplicated() throws AlreadyExistsUserException {
        // arrange
        RegistrationDtoRequest registrationDtoRequest = new RegistrationDtoRequest(
                "testuser", "test@example.com", "password", Form.ELEVENTH, 16
        );
        doNothing().when(validationService).validateEmail(registrationDtoRequest.email());
        doThrow(AlreadyExistsUserException.class).when(validationService).validateUsername(registrationDtoRequest.username());

        //when
        assertThrows(AlreadyExistsUserException.class, () -> underTest.registerAccount(registrationDtoRequest));

        // then
        verify(studentRepository, never()).save(any());
        verify(confirmationEmailTokenRepository, never()).save(any());
    }
}
