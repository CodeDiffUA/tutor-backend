package dev.backend.tutor.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.dtos.auth.RegistrationDtoRequest;
import dev.backend.tutor.dtos.friendship.RequestFriendshipRequestDto;
import dev.backend.tutor.dtos.friendship.RequestFriendshipResponseDto;
import dev.backend.tutor.repositories.student.StudentRepository;
import dev.backend.tutor.utills.student.Form;
import dev.backend.tutor.utills.student.StudentListProcessor;
import dev.backend.tutor.utils.StudentGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class FriendControllerTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine");

    @DynamicPropertySource
    public static void setUpThings(DynamicPropertyRegistry registry) {
        Startables.deepStart(postgreSQLContainer).join();
    }
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SENDER_USERNAME = "SENDER_USERNAME";
    private static final String RECIPIENT_USERNAME = "RECIPIENT_USERNAME";

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    @Transactional
    void Should_SendRequest_When_HttpRequestValid() throws Exception {
        // arrange
        RegistrationDtoRequest registrationDto1 = new RegistrationDtoRequest(
                "login", "email", "password", Form.ELEVENTH, 16);

        RegistrationDtoRequest registrationDto2 = new RegistrationDtoRequest(
                "login1", "email2", "password", Form.ELEVENTH, 16);

        // when/then
        mockMvc.perform(post("/api/v1/student")
                        .content(objectMapper.writeValueAsString(registrationDto1))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/student")
                        .content(objectMapper.writeValueAsString(registrationDto2))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        // when/then
        var requestFriendshipRequestDto = new RequestFriendshipRequestDto("login", "login1");
        mockMvc.perform(post("/api/v1/friendship/request")
                        .content(objectMapper.writeValueAsString(requestFriendshipRequestDto))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void Should_SendRequest_When_StudentNotExist() throws Exception {
        // arrange
        var recipient = StudentGenerator.generateStudent(RECIPIENT_USERNAME);
        studentRepository.saveAll(List.of(recipient));
        RequestFriendshipRequestDto requestFriendshipRequestDto = new RequestFriendshipRequestDto(SENDER_USERNAME, RECIPIENT_USERNAME);

        // when/then
        mockMvc.perform(post("/api/v1/friendship/request")
                        .content(objectMapper.writeValueAsString(requestFriendshipRequestDto))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void Should_SendRequest_When_StudentsAlreadyFriends() throws Exception {
        // arrange
        var sender = StudentGenerator.generateStudent(SENDER_USERNAME);
        var recipient = StudentGenerator.generateStudent(RECIPIENT_USERNAME);
        studentRepository.save(recipient);
        sender.addFriend(recipient);
        studentRepository.save(sender);
        RequestFriendshipRequestDto requestFriendshipRequestDto = new RequestFriendshipRequestDto(SENDER_USERNAME, RECIPIENT_USERNAME);

        // when/then
        mockMvc.perform(post("/api/v1/friendship/request")
                        .content(objectMapper.writeValueAsString(requestFriendshipRequestDto))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("You already have this student in friends"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    @Transactional
    void Should_AcceptRequest() throws Exception {
        // arrange
        var sender = StudentGenerator.generateStudent(SENDER_USERNAME);
        var recipient = StudentGenerator.generateStudent(RECIPIENT_USERNAME);
        studentRepository.save(recipient);
        studentRepository.save(sender);
        RequestFriendshipResponseDto requestFriendshipResponseDto = new RequestFriendshipResponseDto(SENDER_USERNAME, RECIPIENT_USERNAME, true);

        // when/then
        mockMvc.perform(post("/api/v1/friendship/response")
                        .content(objectMapper.writeValueAsString(requestFriendshipResponseDto))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var fetchedStudents = studentRepository.findStudentsByUsernameFetchFriends(sender.getUsername(), recipient.getUsername());
        var fetchedSender = StudentListProcessor.extractStudentFromListByUsername(fetchedStudents, sender.getUsername());
        var fetchedRecipient = StudentListProcessor.extractStudentFromListByUsername(fetchedStudents, recipient.getUsername());
        Assertions.assertThat(fetchedSender.getFriends()).contains(fetchedRecipient);
        Assertions.assertThat(fetchedRecipient.getFriends()).contains(fetchedSender);
    }

    @Test
    @Transactional
    void Should_DeclineRequest() throws Exception {
        // arrange
        var sender = StudentGenerator.generateStudent(SENDER_USERNAME);
        var recipient = StudentGenerator.generateStudent(RECIPIENT_USERNAME);
        studentRepository.save(recipient);
        studentRepository.save(sender);
        RequestFriendshipResponseDto requestFriendshipResponseDto = new RequestFriendshipResponseDto(SENDER_USERNAME, RECIPIENT_USERNAME, false);

        // when/then
        mockMvc.perform(post("/api/v1/friendship/response")
                        .content(objectMapper.writeValueAsString(requestFriendshipResponseDto))
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Assertions.assertThat(sender.getFriends()).doesNotContain(recipient);
        Assertions.assertThat(recipient.getFriends()).doesNotContain(sender);
    }
}