package dev.backend.tutor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.utills.student.Form;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
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

    @Test
    void Should_NotRegister_User_When_RequestContainsAlreadyRegisteredValue() throws Exception {
        // arrange
        RegistrationDtoRequest registrationDto = new RegistrationDtoRequest(
                "login", "email", "password", Form.ELEVENTH, 16);

        // when/then
        mockMvc.perform(post("/api/v1/student")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/student")
                        .content(objectMapper.writeValueAsString(registrationDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("This email already exists"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


}