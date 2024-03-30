package dev.backend.tutor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.dtos.auth.AuthenticationDtoRequest;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.entities.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.emails.ConfirmationEmailTokenRepository;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.sevices.security.refresh.TokenFactory;
import dev.backend.tutor.utills.student.Form;
import dev.backend.tutor.utils.StudentGenerator;
import org.junit.jupiter.api.Disabled;
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
public class AuthControllerTest {
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

//    @Test
//    @Disabled
//    void Should_ConfirmUserRegistration_When_UserRegistered() throws Exception {
//        // arrange
//        var student = StudentGenerator.generateStudent("newStudent");
//        studentRepository.save(student);
//        var userDetails = (getUserDetails(student));
//
//        // when/then
//    }

//    @Test
//    void Should_AuthenticateUser_And_ReturnAccessAndRefreshToken() throws Exception {
//        // arrange
//        var student = StudentGenerator.generateStudent("newStudent");
//        studentRepository.save(student);
//        var authenticationDto = new AuthenticationDtoRequest(student.getUsername(), student.getPassword());
//        // when/then
//        mockMvc.perform(post("/api/v1/authentication")
//                        .content(objectMapper.writeValueAsString(authenticationDto))
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$.accessToken").value("token expired"))
////                .andExpect(cookie().exists("refreshToken"));
////                .andExpect(cookie().value("yourCookieName", "expectedValue"))
//    }


//    private UserDetails getUserDetails(Student student) {
//        return new User(student.getUsername(), student.getPassword(), student.getRoles());
//    }
}
