package dev.backend.tutor.sevices.messages;

import dev.backend.tutor.dtos.ExceptionDto;
import dev.backend.tutor.dtos.messages.MessageDto;
import dev.backend.tutor.dtos.messages.SystemMessageDto;
import dev.backend.tutor.utills.student.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private SimpMessagingTemplate messagingTemplate;

    @InjectMocks
    private MessageService messageService;

    @Captor
    private ArgumentCaptor<String> recipientLoginCaptor;

    @Captor
    private ArgumentCaptor<String> destinationCaptor;

    @Captor
    private ArgumentCaptor<MessageDto> messageDtoCaptor;

    @Captor
    private ArgumentCaptor<ExceptionDto> exceptionDtoCaptor;

    @Test
    public void testSendMessageToUser() {
        // Arrange
        String recipientLogin = "recipient";
        MessageDto messageDto = new MessageDto("sender", recipientLogin, "content", "timestamp");

        // Act
        messageService.sendMessageToUser(recipientLogin, messageDto);

        // Assert
        verify(messagingTemplate).convertAndSendToUser(
                recipientLoginCaptor.capture(), destinationCaptor.capture(), messageDtoCaptor.capture()
        );

        assert recipientLoginCaptor.getValue().equals(recipientLogin);
        assert destinationCaptor.getValue().equals("/queue/notifications");
        assert messageDtoCaptor.getValue().equals(messageDto);
    }

    @Test
    public void testSendExceptionToUser() {
        // Arrange
        String recipientLogin = "recipient";
        ExceptionDto exceptionDto = new ExceptionDto("message", "timestamp");

        // Act
        messageService.sendExceptionToUser(recipientLogin, exceptionDto);

        // Assert
        verify(messagingTemplate).convertAndSendToUser(
                recipientLoginCaptor.capture(), destinationCaptor.capture(), exceptionDtoCaptor.capture()
        );

        assert recipientLoginCaptor.getValue().equals(recipientLogin);
        assert destinationCaptor.getValue().equals("/queue/errors");
        assert exceptionDtoCaptor.getValue().equals(exceptionDto);
    }

    @Test
    public void testMessageDtoForFriendshipRequest() {
        // Arrange
        String senderLogin = "sender";
        String recipientLogin = "recipient";

        // Act
        MessageDto messageDto = messageService.messageDtoForFriendshipRequest(senderLogin, recipientLogin);

        // Assert
        String expectedContent = recipientLogin + ", user " + senderLogin + " wants to become your friend";
        String expectedTimestamp = DateUtil.currentTimeStamp();
        assert messageDto.sender().equals(senderLogin);
        assert messageDto.recipient().equals(recipientLogin);
        assert messageDto.content().equals(expectedContent);
        assert messageDto.timestamp().equals(expectedTimestamp);
    }

    @Test
    public void testExceptionDtoForFriendshipRequest() {
        // Arrange
        String exceptionMessage = "exception message";

        // Act
        ExceptionDto exceptionDto = messageService.exceptionDtoForFriendshipRequest(exceptionMessage);

        // Assert
        String expectedTimestamp = DateUtil.currentTimeStamp();
        assert exceptionDto.message().equals(exceptionMessage);
        assert exceptionDto.timestamp().equals(expectedTimestamp);
    }

    @Test
    public void testSendSystemMessageToUser() {
        // Arrange
        String recipientLogin = "recipient";
        SystemMessageDto messageDto = new SystemMessageDto(recipientLogin, "content", "timestamp");

        // Act
        messageService.sendSystemMessageToUser(recipientLogin, messageDto);

        // Assert
        verify(messagingTemplate).convertAndSendToUser(
                recipientLogin, "/queue/notifications", messageDto
        );
    }

    @Test
    public void testMessageDtoForAcceptingFriendshipRequest() {
        // Arrange
        String recipientLogin = "recipient";

        // Act
        SystemMessageDto messageDto = messageService.messageDtoForAcceptingFriendshipRequest(recipientLogin);

        // Assert
        String expectedContent = recipientLogin + ", your friendship request has been accepted";
        assert messageDto.recipient().equals(recipientLogin);
        assert messageDto.content().equals(expectedContent);
    }

    @Test
    public void testMessageDtoForDecliningFriendshipRequest() {
        // Arrange
        String recipientLogin = "recipient";

        // Act
        SystemMessageDto messageDto = messageService.messageDtoForDecliningFriendshipRequest(recipientLogin);

        // Assert
        String expectedContent = recipientLogin + ", your friendship request has been declined";
        assert messageDto.recipient().equals(recipientLogin);
        assert messageDto.content().equals(expectedContent);
    }
}
