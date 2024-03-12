package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.MessageDto;
import dev.backend.tutor.utills.student.DateUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;


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
    public void sendExceptionToUser(String recipientLogin, ExceptionDto ExceptionDto){
        messagingTemplate.convertAndSendToUser(
                recipientLogin, "/queue/errors", ExceptionDto
        );
    }

    public MessageDto messageDtoForFriendshipRequest(String senderLogin, String recipientLogin) {
        String content = recipientLogin + ", user " + senderLogin + " wants to become your friend";
        String timestamp = DateUtil.currentTimeStamp();
        return new MessageDto(senderLogin, recipientLogin, content, timestamp);
    }

    public ExceptionDto exceptionDtoForFriendshipRequest(String exceptionMessage) {
        var timestamp = DateUtil.currentTimeStamp();
        return new ExceptionDto(exceptionMessage, timestamp);
    }

}
