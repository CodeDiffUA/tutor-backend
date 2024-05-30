package dev.backend.tutor.services.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.backend.tutor.dtos.ai.AiChatMessageDto;
import dev.backend.tutor.dtos.ai.AiRequestDto;
import dev.backend.tutor.dtos.ai.AiResponseDto;
import dev.backend.tutor.dtos.ai.RequestAiChatMessageDto;
import dev.backend.tutor.entities.ai.AiChat;
import dev.backend.tutor.entities.ai.AiChatMessage;
import dev.backend.tutor.entities.student.Student;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.sql.ai.AiChatMessageRepository;
import dev.backend.tutor.repositories.sql.ai.AiChatRepository;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import dev.backend.tutor.services.security.jwt.JwtParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AIAgent {

    private static final String USER_ROLE = "user";
    private static final String ASSISTANT_ROLE = "assistant";

    @Value("${open-ai.url}")
    private String apiUrl;

    @Value("${openai.model}")
    private String model;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final JwtParser jwtParser;
    private final AiChatMessageRepository aiChatMessageRepository;
    private final StudentRepository studentRepository;
    private final AiChatRepository aiChatRepository;

    public AIAgent(RestTemplate restTemplate, ObjectMapper objectMapper, JwtParser jwtParser,
                   AiChatMessageRepository aiChatMessageRepository, StudentRepository studentRepository,
                   AiChatRepository aiChatRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.jwtParser = jwtParser;
        this.aiChatMessageRepository = aiChatMessageRepository;
        this.studentRepository = studentRepository;
        this.aiChatRepository = aiChatRepository;
    }

    @Transactional
    public String requestAI(String accessToken, Long chatId, List<RequestAiChatMessageDto> messages)
            throws JsonProcessingException, NotFoundUserException {
        String username = jwtParser.extractUsername(accessToken);
        Student student = getStudent(username);
        AiChat aiChat = getAiChatById(chatId);

        var newMessage = messages.get(messages.size()-1).content();
        saveMessageFromUser(student, aiChat, newMessage);

        AiRequestDto requestDto = new AiRequestDto(model, 50, messages);
        String jsonRequestBody = objectMapper.writeValueAsString(requestDto);

        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody);
        String response = restTemplate.postForObject(apiUrl, entity, String.class);

        AiResponseDto responseDto = objectMapper.readValue(response, AiResponseDto.class);
        saveMessageFromAi(student, aiChat, responseDto);

        return responseDto.choices().get(0).message().content();
    }

    private void saveMessageFromAi(Student student, AiChat aiChat, AiResponseDto responseDto) {
        String content = responseDto.choices().get(0).message().content();
        AiChatMessage aiChatMessage = new AiChatMessage(content, ASSISTANT_ROLE, student, aiChat);
        aiChatMessageRepository.save(aiChatMessage);
    }

    private AiChat getAiChatById(Long chatId) {
        return aiChatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found chat with id: " + chatId));
    }

    private Student getStudent(String username) throws NotFoundUserException {
        return studentRepository.findStudentByUsername(username)
                .orElseThrow(() -> new NotFoundUserException("Cannot find user " + username));
    }

    private void saveMessageFromUser(Student student, AiChat aiChat, String newMessage) {
        AiChatMessage aiChatMessage = new AiChatMessage(newMessage, USER_ROLE, student, aiChat);
        aiChatMessageRepository.save(aiChatMessage);
    }
}
