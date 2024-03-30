package dev.backend.tutor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.refresh.TokenFactory;
import dev.backend.tutor.utills.student.Form;
import dev.backend.tutor.utils.StudentGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;

import java.time.Instant;
import java.util.Optional;


@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class RegistrationControllerTest {
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
    void Should_NotRegister_User_When_RequestContainsAlreadyRegisteredValue() throws Exception {
        // arrange
        RegistrationDtoRequest registrationDto = new RegistrationDtoRequest(
                "login", "email", "password", Form.ELEVENTH, 16);

        // when/then
        mockMvc.perform(post("/api/v1/student/registration")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isString());

        mockMvc.perform(post("/api/v1/student/registration")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("This email already exists"))
                .andExpect(jsonPath("$.timestamp").isString());

    }

    @Test
    void Should_ConfirmUserRegistration_When_UserRegistered() throws Exception {
        // arrange
        var student = StudentGenerator.generateUnabledStudent("newStudent");
        studentRepository.save(student);
        var userDetails = (getUserDetails(student));
        var token = tokenFactory.createConfirmationEmailToken(userDetails);
        confirmationEmailTokenRepository.save(token);

        // when/then
        mockMvc.perform(post("/api/v1/student/registration/confirm")
                        .param("token", token.getToken()))
                .andExpect(status().isOk());
    }

    @Test
    void Should_ReturnNotFoundUserMessage_When_TokenNotInDB() throws Exception {
        // arrange
        var invalidToken = "invalidToken";

        // when/then
        mockMvc.perform(post("/api/v1/student/registration/confirm")
                        .param("token", invalidToken))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("token not found"))
                .andExpect(jsonPath("$.timestamp").isString());
    }

    @Test
    void Should_ReturnNotFoundUserMessage_When_TokenExpired() throws Exception {
        // arrange
        var student = StudentGenerator.generateUnabledStudent("newStudent");
        studentRepository.save(student);
        var userDetails = (getUserDetails(student));
        var token = tokenFactory.createConfirmationEmailToken(userDetails);
        token.setExpiryDate(Instant.now().minusMillis(3600));
        confirmationEmailTokenRepository.save(token);
        // when/then
        mockMvc.perform(post("/api/v1/student/registration/confirm")
                        .param("token", token.getToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("token expired"))
                .andExpect(jsonPath("$.timestamp").isString());
    }


    private UserDetails getUserDetails(Student student) {
        return new User(student.getUsername(), student.getPassword(), student.getRoles());
    }
}