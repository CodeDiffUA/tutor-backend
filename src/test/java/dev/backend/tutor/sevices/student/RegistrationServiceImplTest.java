package dev.backend.tutor.sevices.student;

import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
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
    void Should_RegisterAccountSuccessful_When_RequestValid() {
        // arrange
        RegistrationDtoRequest registrationDtoRequest = new RegistrationDtoRequest(
                "newUsername", "newEmail", "password", Form.ELEVENTH, 16
        );
        lenient().doReturn(false).when(studentRepository).existsStudentByUsername(registrationDtoRequest.username());
        lenient().doReturn(false).when(studentRepository).existsStudentByEmail(registrationDtoRequest.email());

        // when
        assertDoesNotThrow(() -> underTest.registerAccount(registrationDtoRequest));

        // then
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void Should_FailRegistration_When_UsernameDuplicated() {
        // arrange
        RegistrationDtoRequest registrationDtoRequest = new RegistrationDtoRequest(
                "newUsername", "newEmail", "password", Form.ELEVENTH, 16
        );

        lenient().doReturn(true).when(studentRepository).existsStudentByUsername(registrationDtoRequest.username());
        lenient().doReturn(false).when(studentRepository).existsStudentByEmail(registrationDtoRequest.email());


        // then
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void Should_FailRegistration_When_EmailDuplicated() {
        // arrange
        RegistrationDtoRequest registrationDtoRequest = new RegistrationDtoRequest(
                "newUsername", "newEmail", "password", Form.ELEVENTH, 16
        );
        lenient().doReturn(false).when(studentRepository).existsStudentByUsername(registrationDtoRequest.username());
        lenient().doReturn(true).when(studentRepository).existsStudentByEmail(registrationDtoRequest.email());

        // then
        verify(studentRepository, never()).save(any(Student.class));
    }
}
