package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.messages.MessageDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderImpl implements MessageSender{

    private final SimpMessagingTemplate messagingTemplate;

    public MessageSenderImpl(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendMessageToUser(String recipientLogin, MessageDto messageDto){
        messagingTemplate.convertAndSendToUser(
                recipientLogin, "/queue/notifications", messageDto
        );
    }

    public void sendSystemMessageToUser(String recipientLogin, SystemMessageDto messageDto){
        messagingTemplate.convertAndSendToUser(
                recipientLogin, "/queue/notifications", messageDto
        );
    }

    public void sendExceptionToUser(String recipientLogin, ExceptionDto exceptionDto){
        messagingTemplate.convertAndSendToUser(
                recipientLogin, "/queue/errors", exceptionDto
        );
    }
}
