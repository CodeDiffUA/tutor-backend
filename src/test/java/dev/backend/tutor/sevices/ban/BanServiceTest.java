package dev.backend.tutor.sevices.ban;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.student.BanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class BanServiceTest {

    @InjectMocks
    private BanService banService;

    @Mock
    private StudentRepository studentRepository;

    @Test
    void Should_BanUser() {
        // Arrange
        String usernameOrEmail = "test@test.com";
        Student student = new Student();
        student.setIsBanned(false);

        when(studentRepository.findStudentsByUsernameOrEmailWithRoles(usernameOrEmail)).thenReturn(Optional.of(student));

        // Act
        banService.banStudent(usernameOrEmail);

        // Assert
        Assertions.assertTrue(student.getIsBanned());
        verify(studentRepository, times(1)).save(student);
    }
    @Test
    void Should_UnbanUser(){
        // Arrange
        String usernameOrEmail = "test@test.com";
        Student student = new Student();
        student.setIsBanned(true);

        when(studentRepository.findStudentsByUsernameOrEmailWithRoles(usernameOrEmail)).thenReturn(Optional.of(student));

        // Act
        banService.unbanStudent(usernameOrEmail);

        // Assert
        Assertions.assertFalse(student.getIsBanned());
        verify(studentRepository, times(1)).save(student);
    }
}