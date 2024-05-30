package dev.backend.tutor.services.ai;

import dev.backend.tutor.dtos.ai.AiChatMessageDto;
import dev.backend.tutor.dtos.ai.AiChatResponseDto;
import dev.backend.tutor.entities.ai.AiChat;
import dev.backend.tutor.entities.ai.AiChatMessage;
import dev.backend.tutor.exceptions.NotFoundChatException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.repositories.sql.ai.AiChatMessageRepository;
import dev.backend.tutor.repositories.sql.ai.AiChatRepository;
import dev.backend.tutor.repositories.sql.student.StudentRepository;
import dev.backend.tutor.services.security.jwt.JwtParser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AiService {

    private final AiChatRepository aiChatRepository;
    private final StudentRepository studentRepository;
    private final AiChatMessageRepository aiChatMessageRepository;
    private final JwtParser jwtParser;

    public AiService(AiChatRepository aiChatRepository, StudentRepository studentRepository, AiChatMessageRepository aiChatMessageRepository, JwtParser jwtParser) {
        this.aiChatRepository = aiChatRepository;
        this.studentRepository = studentRepository;
        this.aiChatMessageRepository = aiChatMessageRepository;
        this.jwtParser = jwtParser;
    }

    @Transactional
    public AiChat createChat(String accessToken, String chatName) throws NotFoundUserException {
        var studentUsername = jwtParser.extractUsername(accessToken);
        var student = studentRepository.findStudentByUsername(studentUsername)
                .orElseThrow(() -> new NotFoundUserException(studentUsername));

        var chat = AiChat
                .builder()
                .student(student)
                .name(chatName)
                .build();

        return aiChatRepository.save(chat);
    }

    @Transactional(readOnly = true)
    public List<AiChatResponseDto> loadAllStudentsChats(String jwt) {
        String username = jwtParser.extractUsername(jwt);
        return aiChatRepository.findAiChatByStudent(username);

    }

    @Transactional(readOnly = true)
    public Page<AiChatMessageDto> loadMessagesFromStudentsChatByChatID(
            int chatId, Pageable pageable) {
        return aiChatMessageRepository.getAiChatMessagesByChatId(chatId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<AiChatMessageDto> loadMessagesFromStudentsChatByUsernameAndByChatName(
            String jwt, String chatName, Pageable pageable) {
        var username = jwtParser.extractUsername(jwt);
        return aiChatMessageRepository.getAiChatMessageByStudentByChat(username, chatName, pageable);
    }

    @Transactional
    public void deleteChatById(Long id) {
        aiChatRepository.deleteById(id);
    }

    @Transactional
    public void changeChatName(Long id, String newChatName) throws NotFoundChatException {
        AiChat aiChat = aiChatRepository.findById(id)
                .orElseThrow(() -> new NotFoundChatException("no such a chat with id: " + id));
        aiChat.setName(newChatName);
    }
}
