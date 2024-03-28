package dev.backend.tutor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.refresh.TokenFactory;
import dev.backend.tutor.utils.StudentGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class SenderEmailControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @DynamicPropertySource
    public static void setUpThings(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgreSQLContainer).join();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TokenFactory tokenFactory;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ConfirmationEmailTokenRepository confirmationEmailTokenRepository;

    @Test
    void Should_SendVerificationMessageOnEmail() throws Exception {
        // arrange
        var student = StudentGenerator.generateUnabledStudent("newStudent");
        studentRepository.save(student);
        var userDetails = new User(student.getUsername(), student.getPassword(), student.getRoles());
        var token = tokenFactory.createConfirmationEmailToken(userDetails);
        confirmationEmailTokenRepository.save(token);

        // when/then
        mockMvc.perform(get("/api/v1/email/send-verification")
                        .param("token", token.getToken())
                        .param("email", student.getEmail()))
                .andExpect(status().isOk());
    }


    @Test
    void ShouldThrowNotFoundUserException_When_EmailInvalid() throws Exception {
        // arrange
        var student = StudentGenerator.generateUnabledStudent("newStudent");
        studentRepository.save(student);
        var userDetails = new User(student.getUsername(), student.getPassword(), student.getRoles());
        var token = tokenFactory.createConfirmationEmailToken(userDetails);
        confirmationEmailTokenRepository.save(token);

        // when/then
        mockMvc.perform(get("/api/v1/email/send-verification")
                        .param("token", token.getToken())
                        .param("email", "email@gmail.com"))
                .andExpect(status().isNotFound());
    }


    @Test
    void ShouldThrowInvalidTokenException() throws Exception {
        // arrange
        var student = StudentGenerator.generateUnabledStudent("newStudent");
        studentRepository.save(student);

        // when/then
        mockMvc.perform(get("/api/v1/email/send-verification")
                        .param("token", "token")
                        .param("email", student.getEmail()))
                .andExpect(status().isNotFound());
    }
}