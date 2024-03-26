package dev.backend.tutor.sevices.validation;

import dev.backend.tutor.exceptions.AlreadyExistsUserException;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.registration.validation.StudentValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentValidationServiceTest {

    @InjectMocks
    private StudentValidationService validationService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void testValidateEmailWhenEmailDoesNotExist() {
        // when
        when(studentRepository.existsStudentByEmail(any())).thenReturn(false);

        // assert
        assertDoesNotThrow(() -> validationService.validateEmail("newEmail"));
    }

    @Test
    void testValidateEmailWhenEmailExists() {
        // when
        when(studentRepository.existsStudentByEmail(any())).thenReturn(true);

        // assert
        assertThrows(AlreadyExistsUserException.class, () -> validationService.validateEmail("existingEmail"));
    }

    @Test
    void testValidateUsernameWhenUsernameDoesNotExist() {
        // when
        when(studentRepository.existsStudentByUsername(any())).thenReturn(false);

        // assert
        assertDoesNotThrow(() -> validationService.validateUsername("newUsername"));
    }

    @Test
    void testValidateUsernameWhenUsernameExists() {
        // when
        when(studentRepository.existsStudentByUsername(any())).thenReturn(true);

        // assert
        assertThrows(AlreadyExistsUserException.class, () -> validationService.validateUsername("existingUsername"));
    }
}
