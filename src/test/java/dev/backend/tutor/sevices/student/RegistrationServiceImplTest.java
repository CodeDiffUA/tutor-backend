package dev.backend.tutor.sevices.student;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.repositories.StudentRepository;
import dev.backend.tutor.sevices.registration.RegistrationServiceImpl;
import dev.backend.tutor.sevices.validation.StudentValidationService;
import dev.backend.tutor.utills.student.Form;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private RegistrationServiceImpl underTest;

    @Mock
    private StudentValidationService validationService;

    @Test
    void Should_RegisterAccountSuccessful_When_RequestValid() throws AlreadyExistsUserException {
        // arrange
        RegistrationDtoRequest registrationDtoRequest = new RegistrationDtoRequest(
                "testuser", "test@example.com", "password", Form.ELEVENTH, 16
        );

        doNothing().when(validationService).validateEmail(registrationDtoRequest.email());
        doNothing().when(validationService).validateUsername(registrationDtoRequest.username());

        // when
        assertDoesNotThrow(() -> underTest.registerAccount(registrationDtoRequest));

        // then
        verify(studentRepository, times(1)).save(any(Student.class));
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
    }
}
