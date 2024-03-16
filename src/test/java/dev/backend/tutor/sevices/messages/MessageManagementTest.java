package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.messages.MessageDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;
import dev.backend.tutor.utills.student.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessageManagementTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private MessageSenderImpl messageSender;

    @Test
    public void testMessageDtoForAcceptingFriendshipRequest() {
        String recipientLogin = "recipient";
        SystemMessageDto messageDto = MessageProvider.messageDtoForAcceptingFriendshipRequest(recipientLogin);
        assertNotNull(messageDto);
        assertEquals(recipientLogin + ", your friendship request has been accepted", messageDto.content());
    }

    @Test
    public void testMessageDtoForDecliningFriendshipRequest() {
        String recipientLogin = "recipient";
        SystemMessageDto messageDto = MessageProvider.messageDtoForDecliningFriendshipRequest(recipientLogin);
        assertNotNull(messageDto);
        assertEquals(recipientLogin + ", your friendship request has been declined", messageDto.content());
    }

    @Test
    public void testMessageDtoForFriendshipRequest() {
        String senderLogin = "sender";
        String recipientLogin = "recipient";
        MessageDto messageDto = MessageProvider.messageDtoForFriendshipRequest(senderLogin, recipientLogin);
        assertNotNull(messageDto);
        Assertions.assertEquals(recipientLogin + ", user " + senderLogin + " wants to become your friend", messageDto.content());
    }

    @Test
    public void testExceptionDtoForFriendshipRequest() {
        String exceptionMessage = "Exception message";
        ExceptionDto exceptionDto = MessageProvider.exceptionDtoForFriendshipRequest(exceptionMessage);
        assertNotNull(exceptionDto);
        assertEquals(exceptionMessage, exceptionDto.message());
    }

    @Test
    public void testSendMessageToUser() {
        String recipientLogin = "recipient";
        MessageDto messageDto = new MessageDto("sender", recipientLogin, "content", DateUtil.currentTimeStamp());
        messageSender.sendMessageToUser(recipientLogin, messageDto);
        verify(messagingTemplate).convertAndSendToUser(recipientLogin, "/queue/notifications", messageDto);
    }

    @Test
    public void testSendSystemMessageToUser() {
        String recipientLogin = "recipient";
        SystemMessageDto messageDto = new SystemMessageDto(recipientLogin, "content", "timestamp");
        messageSender.sendSystemMessageToUser(recipientLogin, messageDto);
        verify(messagingTemplate).convertAndSendToUser(recipientLogin, "/queue/notifications", messageDto);
    }

    @Test
    public void testSendExceptionToUser() {
        String recipientLogin = "recipient";
        ExceptionDto exceptionDto = new ExceptionDto("message", "timestamp");
        messageSender.sendExceptionToUser(recipientLogin, exceptionDto);
        verify(messagingTemplate).convertAndSendToUser(recipientLogin, "/queue/errors", exceptionDto);
    }
}
