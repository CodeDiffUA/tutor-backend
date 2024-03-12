package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.MessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
public class MessageService {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToUser(String recipientLogin, MessageDto messageDto){
        messagingTemplate.convertAndSendToUser(
            recipientLogin, "/queue/notifications", messageDto
        );
    }

}
