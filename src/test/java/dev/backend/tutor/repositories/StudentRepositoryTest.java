package dev.backend.tutor.repositories;

import dev.backend.tutor.entities.Student;
import dev.backend.tutor.utils.StudentGenerator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private EntityManager entityManager;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @DynamicPropertySource
    public static void setUpThings(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgreSQLContainer).join();
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    @Transactional
    public void Should_SuccessfullyLoadPairOfStudents() {
        // arrange
        Student firstStudent = StudentGenerator.generateStudent(1);
        Student secondStudent = StudentGenerator.generateStudent(2);
        studentRepository.saveAll(List.of(firstStudent, secondStudent));

        // when
        List<Student> result = studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                firstStudent.getUsername(), secondStudent.getUsername());

        // Assert
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(firstStudent));
        Assertions.assertTrue(result.contains(secondStudent));

        for (Student student : result) {
            assertNotNull(student.getFriends());
            assertNotNull(student.getBlockedStudents());
        }
    }

    @Test
    @Transactional
    public void Should_SuccessfullyLoadPairOfStudents_And_Students_Should_BeFriends() {
        // arrange
        Student firstStudent = StudentGenerator.generateStudent(1);
        Student secondStudent = StudentGenerator.generateStudent(2);
        studentRepository.save(secondStudent);
        firstStudent.addFriend(secondStudent);
        studentRepository.save(firstStudent);
        studentRepository.save(secondStudent);


        // when
        List<Student> result = studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                firstStudent.getUsername(), secondStudent.getUsername());

        // Assert
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(firstStudent));
        Assertions.assertTrue(result.contains(secondStudent));

        var firstResult = result.get(0);
        var secondResult = result.get(1);

        assertThat(firstResult.getFriends()).contains(secondResult);
        assertThat(secondResult.getFriends()).contains(firstResult);

    }

    @Test
    @Transactional
    public void Should_SuccessfullyLoadPairOfStudents_And_Student_Should_BeBlocked() {
        // arrange
        Student firstStudent = StudentGenerator.generateStudent(1);
        Student secondStudent = StudentGenerator.generateStudent(2);
        studentRepository.save(secondStudent);
        firstStudent.blockStudent(secondStudent);
        studentRepository.save(firstStudent);
        // when
        List<Student> result = studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                firstStudent.getUsername(), secondStudent.getUsername());

        // Assert
        assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(firstStudent));
        Assertions.assertTrue(result.contains(secondStudent));

        var firstStudentExpected = result.stream()
                .filter(student -> student.getUsername().equals(firstStudent.getUsername()))
                .findFirst()
                .orElseThrow();
        var secondStudentExpected = result.stream()
                .filter(student -> student.getUsername().equals(secondStudent.getUsername()))
                .findFirst()
                .orElseThrow();

        assertThat(firstStudentExpected.getBlockedStudents()).contains(secondStudentExpected);

    }
    @Test
    @Transactional
    public void Should_ReturnEmptyList_When_SenderNotExist() {
        // Act
        List<Student> result = studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                "nonexistentSender", "existingRecipient");

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    @Transactional
    public void Should_ReturnEmptyList_When_RecipientNotExist() {
        // Arrange
        Student existingSender = StudentGenerator.generateStudent(1);

        // Act
        List<Student> result = studentRepository.findSenderAndRecipientStudentsWithFriendsAndBlocked(
                existingSender.getUsername(), "nonexistentRecipient");

        // Assert
        Assertions.assertTrue(result.isEmpty());
    }

}