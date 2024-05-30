package dev.backend.tutor.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.backend.tutor.dtos.ai.*;
import dev.backend.tutor.exceptions.NotFoundChatException;
import dev.backend.tutor.exceptions.NotFoundUserException;
import dev.backend.tutor.services.ai.AIAgent;
import dev.backend.tutor.services.ai.AiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/ai")
public class AiController {

    private final AIAgent aiAgent;
    private final AiService aiService;

    public AiController(AIAgent aiAgent, AiService aiService) {
        this.aiAgent = aiAgent;
        this.aiService = aiService;
    }

    @PostMapping(value = "/chat/help")
    public ResponseEntity<ResponseFromAiHelper> requestAi(@RequestBody RequestToAiHelper request) throws JsonProcessingException, NotFoundUserException {
        return ResponseEntity.ok(new ResponseFromAiHelper(aiAgent.requestAI(request.accessToken(), request.chatId(), request.messages())));
    }


    /**
     * @param accessToken token that contains username
     * @return student's list of all his ai chats
     */
    @GetMapping(value = "/chat/{accessToken}")
    public ResponseEntity<List<AiChatResponseDto>> getStudentsChats(@PathVariable("accessToken") String accessToken) {
        return ResponseEntity.ok(aiService.loadAllStudentsChats(accessToken));
    }

    @PostMapping(value = "/chat")
    public AiChatResponseDto createChat(@RequestBody CreateAiChatRequestDto chatRequestDto) throws NotFoundUserException {
        var chat = aiService.createChat(chatRequestDto.accessToken(), chatRequestDto.chatName());
        return new AiChatResponseDto(chat.getId(), chat.getStudent().getUsername(), chat.getName());

    }

    @GetMapping(value = "/chat")
    public ResponseEntity<List<AiChatMessageDto>> getChatMessagesById(
            @RequestParam("chatId") int id,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(aiService.loadMessagesFromStudentsChatByChatID(id, pageable).getContent());
    }

    @GetMapping(value = "/chat/{accessToken}/{chatName}")
    public ResponseEntity<List<AiChatMessageDto>> getChatMessagesByStudentByChatName(
            @PathVariable("accessToken") String accessToken,
            @PathVariable("chatName") String chatName,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "page", defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(aiService.loadMessagesFromStudentsChatByUsernameAndByChatName(accessToken, chatName, pageable).getContent());
    }

    @DeleteMapping(value = "/chat")
    public ResponseEntity<Void> deleteChatById(
            @RequestParam("chatId") Long id
    ) {
        aiService.deleteChatById(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(value = "/chat")
    public ResponseEntity<Void> changeChatName(
            @RequestParam("chatId") Long id,
            @RequestParam("chatName") String chatName
    ) throws NotFoundChatException {
        aiService.changeChatName(id, chatName);
        return ResponseEntity.ok().build();
    }

}
