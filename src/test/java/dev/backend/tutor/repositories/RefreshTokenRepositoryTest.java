package dev.backend.tutor.repositories;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.entities.auth.RefreshToken;
import dev.backend.tutor.repositories.refresh.RefreshTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.utils.StudentGenerator;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class RefreshTokenRepositoryTest {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @DynamicPropertySource
    public static void setUpThings(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgreSQLContainer).join();
    }

    @Test
    void Should_LoadRefreshTokenWithStudentAndWithHisRoles() {
        // arrange
        var student = StudentGenerator.generateStudent("student");
        studentRepository.save(student);
        var refreshToken = RefreshToken.builder()
                .withToken(UUID.randomUUID().toString())
                .withStudent(student)
                .buildRefreshToken();
        refreshTokenRepository.save(refreshToken);

        // when
        RefreshToken result = refreshTokenRepository
                .findByTokenWithStudentAndHisRoles(refreshToken.getToken())
                .orElseThrow();

        // then
        var loadedStudent = result.getStudent();
        assertThat(result).isEqualTo(refreshToken);
        assertThat(loadedStudent).isEqualTo(student);
        assertThat(loadedStudent.getRoles()).isNotEmpty();
        assertThat(loadedStudent
                .getRoles()
                .stream()
                .findFirst()
                .orElseThrow()
                .getAuthority())
                .isEqualTo(student.getRoles()
                        .stream()
                        .findFirst()
                        .orElseThrow()
                        .getAuthority());
    }
}